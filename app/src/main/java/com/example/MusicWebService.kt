package com.example

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object MusicWebService {
    private const val TAG = "MusicWebService"

    /**
     * Search songs on MusicBrainz by title and artist, or general fallback artist tracks.
     * Returns a list of real Track objects detected from the response.
     */
    suspend fun searchOnlineSongs(title: String, artist: String, defaultGenre: String): List<Track> = withContext(Dispatchers.IO) {
        val tracksList = mutableListOf<Track>()
        try {
            // Build query
            val queryParts = mutableListOf<String>()
            if (title.isNotBlank()) {
                queryParts.add("recording:\"${title.trim()}\"")
            }
            if (artist.isNotBlank()) {
                queryParts.add("artist:\"${artist.trim()}\"")
            } else if (title.isBlank()) {
                // If everything is blank, search some random songs matching default style
                queryParts.add("tag:\"${defaultGenre.lowercase()}\"")
            }

            if (queryParts.isEmpty()) {
                return@withContext emptyList()
            }

            val query = queryParts.joinToString(" AND ")
            val encodedQuery = URLEncoder.encode(query, "UTF-8")
            val urlString = "https://musicbrainz.org/ws/2/recording/?query=$encodedQuery&limit=15&fmt=json"

            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            // Set friendly User-Agent as required by MusicBrainz API guidelines to prevent 403 blocks
            connection.setRequestProperty("User-Agent", "MixFlow/1.0.0 (charlesalexandre.hamon@outlook.fr)")
            connection.connectTimeout = 8000
            connection.readTimeout = 8000

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val jsonStr = reader.use { it.readText() }
                val root = JSONObject(jsonStr)
                if (root.has("recordings")) {
                    val recordings = root.getJSONArray("recordings")
                    for (i in 0 until recordings.length()) {
                        val rec = recordings.getJSONObject(i)
                        val songTitle = rec.optString("title", "")
                        
                        // Parse duration in ms
                        val lengthMs = rec.optInt("length", 0)
                        if (lengthMs <= 0 || songTitle.isBlank()) continue
                        val durationSeconds = lengthMs / 1000

                        // Parse artist credit
                        var songArtist = "Artiste Inconnu"
                        if (rec.has("artist-credit")) {
                            val artistCredit = rec.getJSONArray("artist-credit")
                            if (artistCredit.length() > 0) {
                                songArtist = artistCredit.getJSONObject(0).optString("name", "Artiste Inconnu")
                            }
                        }

                        // Use our local detector to find the best match style, or fall back to default
                        val detectedGenre = TrackDatabase.detectGenre(songTitle, songArtist) ?: defaultGenre

                        tracksList.add(
                            Track(
                                title = songTitle,
                                artist = songArtist,
                                genre = detectedGenre,
                                durationSeconds = durationSeconds
                            )
                        )
                    }
                }
            } else {
                Log.e(TAG, "MusicBrainz API error response code: $responseCode")
            }
            connection.disconnect()
        } catch (e: Exception) {
            Log.e(TAG, "Network exception or timeout during search: ${e.localizedMessage}")
        }
        return@withContext tracksList
    }

    /**
     * Retrieve additional open tracks if the user simply specified a style, to offer more real online pieces.
     */
    suspend fun getTracksByStyle(style: String): List<Track> = withContext(Dispatchers.IO) {
        val tracksList = mutableListOf<Track>()
        try {
            val styleQuery = URLEncoder.encode("tag:\"${style.lowercase()}\"", "UTF-8")
            val urlString = "https://musicbrainz.org/ws/2/recording/?query=$styleQuery&limit=25&fmt=json"

            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("User-Agent", "MixFlow/1.0.0 (charlesalexandre.hamon@outlook.fr)")
            connection.connectTimeout = 8000
            connection.readTimeout = 8000

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val jsonStr = reader.use { it.readText() }
                val root = JSONObject(jsonStr)
                if (root.has("recordings")) {
                    val recordings = root.getJSONArray("recordings")
                    for (i in 0 until recordings.length()) {
                        val rec = recordings.getJSONObject(i)
                        val songTitle = rec.optString("title", "")
                        val lengthMs = rec.optInt("length", 0)
                        if (lengthMs <= 0 || songTitle.isBlank()) continue
                        val durationSeconds = lengthMs / 1000

                        var songArtist = "Artiste Inconnu"
                        if (rec.has("artist-credit")) {
                            val artistCredit = rec.getJSONArray("artist-credit")
                            if (artistCredit.length() > 0) {
                                songArtist = artistCredit.getJSONObject(0).optString("name", "Artiste Inconnu")
                            }
                        }

                        tracksList.add(
                            Track(
                                title = songTitle,
                                artist = songArtist,
                                genre = style,
                                durationSeconds = durationSeconds
                            )
                        )
                    }
                }
            }
            connection.disconnect()
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching tag recordings: ${e.localizedMessage}")
        }
        return@withContext tracksList
    }
}
