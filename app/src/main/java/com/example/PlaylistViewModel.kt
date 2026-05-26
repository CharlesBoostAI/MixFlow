package com.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs

class PlaylistViewModel : ViewModel() {

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _selectedTheme = MutableStateFlow("Chill / Détente")
    val selectedTheme: StateFlow<String> = _selectedTheme.asStateFlow()

    val availableThemes = listOf(
        "Chill / Détente",
        "Sport / Dynamique",
        "Soirée / Fête",
        "Focus / Concentration",
        "Voyage / Road Trip",
        "Romantique",
        "Nostalgique / Rétro"
    )

    private val _songTitle = MutableStateFlow("")
    val songTitle: StateFlow<String> = _songTitle.asStateFlow()

    private val _artistName = MutableStateFlow("")
    val artistName: StateFlow<String> = _artistName.asStateFlow()

    private val _selectedGenre = MutableStateFlow("Rock")
    val selectedGenre: StateFlow<String> = _selectedGenre.asStateFlow()

    private val _hours = MutableStateFlow("")
    val hours: StateFlow<String> = _hours.asStateFlow()

    private val _minutes = MutableStateFlow("")
    val minutes: StateFlow<String> = _minutes.asStateFlow()

    private val _isNoLimit = MutableStateFlow(false)
    val isNoLimit: StateFlow<Boolean> = _isNoLimit.asStateFlow()

    private val _generatedPlaylist = MutableStateFlow<List<Track>>(emptyList())
    val generatedPlaylist: StateFlow<List<Track>> = _generatedPlaylist.asStateFlow()

    private val _hasGenerated = MutableStateFlow(false)
    val hasGenerated: StateFlow<Boolean> = _hasGenerated.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg.asStateFlow()

    private val _autoDetectedGenre = MutableStateFlow<String?>(null)
    val autoDetectedGenre: StateFlow<String?> = _autoDetectedGenre.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _playlistSource = MutableStateFlow<String?>(null)
    val playlistSource: StateFlow<String?> = _playlistSource.asStateFlow()

    // Supported Genres
    val availableGenres = listOf(
        "Rock", "Pop", "Rap", "Phonk", "Électro", "Jazz", "Metal", "Variété Française",
        "Reggae", "Classique", "Blues", "Lo-Fi", "Country", "Disco", "K-Pop", "R&B"
    )

    fun selectTab(index: Int) {
        _selectedTab.value = index
        _errorMsg.value = null
    }

    fun selectTheme(theme: String) {
        _selectedTheme.value = theme
    }

    fun updateSongTitle(title: String) {
        _songTitle.value = title
        triggerGenreDetection(title, _artistName.value)
    }

    fun updateArtistName(artist: String) {
        _artistName.value = artist
        triggerGenreDetection(_songTitle.value, artist)
    }

    private fun triggerGenreDetection(title: String, artist: String) {
        val detected = TrackDatabase.detectGenre(title, artist)
        _autoDetectedGenre.value = detected
        if (detected != null && detected in availableGenres) {
            _selectedGenre.value = detected
        }
    }

    fun selectGenre(genre: String) {
        _selectedGenre.value = genre
        // Clear detected indicator if the user explicitly overrides the selection and it doesn't match
        if (_autoDetectedGenre.value != genre) {
            _autoDetectedGenre.value = null
        }
    }

    fun updateHours(h: String) {
        // Only allow digits
        if (h.all { it.isDigit() }) {
            _hours.value = h
        }
    }

    fun updateMinutes(m: String) {
        // Only allow digits
        if (m.all { it.isDigit() }) {
            _minutes.value = m
        }
    }

    fun toggleNoLimit(value: Boolean) {
        _isNoLimit.value = value
        if (value) {
            _errorMsg.value = null
        }
    }

    fun generatePlaylist() {
        _errorMsg.value = null
        _playlistSource.value = null

        viewModelScope.launch {
            val h = _hours.value.toIntOrNull() ?: 0
            val m = _minutes.value.toIntOrNull() ?: 0
            val totalSeconds = (h * 3600) + (m * 60)

            if (!_isNoLimit.value && totalSeconds <= 0) {
                _errorMsg.value = "Veuillez entrer une durée valide (ex: 30 minutes) ou cocher 'Je m'en fiche'."
                return@launch
            }

            _isLoading.value = true

            // Capture state variables to safety-copy before switching threads
            val isNoLimitVal = _isNoLimit.value
            val selectedTabVal = _selectedTab.value
            val selectedThemeVal = _selectedTheme.value
            val selectedGenreVal = _selectedGenre.value
            val songTitleVal = _songTitle.value
            val artistNameVal = _artistName.value

            try {
                val resultInBg = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Default) {
                    // Determine primary genre based on active tab
                    val primaryGenre = if (selectedTabVal == 0) {
                        selectedGenreVal
                    } else {
                        when (selectedThemeVal) {
                            "Chill / Détente" -> "Jazz"
                            "Sport / Dynamique" -> "Phonk"
                            "Soirée / Fête" -> "Électro"
                            "Focus / Concentration" -> "Jazz"
                            "Voyage / Road Trip" -> "Rock"
                            "Romantique" -> "Pop"
                            "Nostalgique / Rétro" -> "Variété Française"
                            else -> "Pop"
                        }
                    }

                    val titleQuery = if (selectedTabVal == 0) songTitleVal else ""
                    val artistQuery = if (selectedTabVal == 0) artistNameVal else ""

                    // Gather candidates: First try online priority, then fallback to local
                    val baseSongs = if (selectedTabVal == 0) {
                        TrackDatabase.songs.filter { it.genre.equals(primaryGenre, ignoreCase = true) }
                    } else {
                        val themeGenres = when (selectedThemeVal) {
                            "Chill / Détente" -> listOf("Jazz", "Pop")
                            "Sport / Dynamique" -> listOf("Phonk", "Électro", "Rap")
                            "Soirée / Fête" -> listOf("Pop", "Électro", "Rap")
                            "Focus / Concentration" -> listOf("Jazz")
                            "Voyage / Road Trip" -> listOf("Rock", "Pop")
                            "Romantique" -> listOf("Pop", "Jazz")
                            "Nostalgique / Rétro" -> listOf("Variété Française", "Rock")
                            else -> listOf("Pop")
                        }
                        TrackDatabase.songs.filter { song -> themeGenres.any { it.equals(song.genre, ignoreCase = true) } }
                    }

                    val candidatePool = mutableListOf<Track>()
                    var sourceFound = ""

                    try {
                        // Fetch dynamic songs matching criteria FIRST
                        val onlineSongs = if (selectedTabVal == 0) {
                            if (titleQuery.isNotBlank() || artistQuery.isNotBlank()) {
                                MusicWebService.searchOnlineSongs(titleQuery, artistQuery, primaryGenre)
                            } else {
                                MusicWebService.getTracksByStyle(primaryGenre)
                            }
                        } else {
                            MusicWebService.getTracksByStyle(primaryGenre)
                        }

                        if (onlineSongs.isNotEmpty()) {
                            candidatePool.addAll(onlineSongs)
                            sourceFound = "INTERNET"
                        } else {
                            // Empty online list -> Fallback to SHUFFLED local base catalogue
                            candidatePool.addAll(baseSongs.shuffled())
                            sourceFound = "LOCAL"
                        }
                    } catch (e: Exception) {
                        // Network failure/timeout -> Fallback to SHUFFLED local base catalogue
                        candidatePool.addAll(baseSongs.shuffled())
                        sourceFound = "LOCAL"
                    }

                    val finalPlaylist = if (isNoLimitVal) {
                        // "Je m'en fiche" mode -> Exactly 15 to 22 tracks
                        val resultSongs = candidatePool.toMutableList()
                        if (resultSongs.size < 15) {
                            val related = TrackDatabase.getRelatedGenres(primaryGenre)
                            val relatedSongs = TrackDatabase.songs
                                .filter { song -> related.any { relGenre -> song.genre.equals(relGenre, ignoreCase = true) } }
                                .shuffled()
                            
                            for (song in relatedSongs) {
                                if (resultSongs.size >= 16) break
                                val isDuplicate = resultSongs.any {
                                    it.title.equals(song.title, ignoreCase = true) && 
                                    it.artist.equals(song.artist, ignoreCase = true)
                                }
                                if (!isDuplicate) {
                                    resultSongs.add(song)
                                }
                            }
                        }
                        resultSongs.shuffled().take(18)
                    } else {
                        // Defined time mode
                        val totalPoolDuration = candidatePool.sumOf { it.durationSeconds }
                        if (totalPoolDuration < totalSeconds + 600) {
                            val related = TrackDatabase.getRelatedGenres(primaryGenre)
                            val relatedSongs = TrackDatabase.songs
                                .filter { song -> related.any { relGenre -> song.genre.equals(relGenre, ignoreCase = true) } }
                                .shuffled()
                            
                            for (song in relatedSongs) {
                                if (candidatePool.sumOf { it.durationSeconds } >= totalSeconds + 1200) break
                                val isDuplicate = candidatePool.any {
                                    it.title.equals(song.title, ignoreCase = true) && 
                                    it.artist.equals(song.artist, ignoreCase = true)
                                }
                                if (!isDuplicate) {
                                    candidatePool.add(song)
                                }
                            }
                        }

                        // Run a heuristic solver with 900 iterations to find subset that hits closest to target duration
                        var bestSet = listOf<Track>()
                        var bestDelta = Int.MAX_VALUE

                        for (attempt in 0 until 900) {
                            val shuffled = candidatePool.shuffled()
                            val currentSet = mutableListOf<Track>()
                            var currentSum = 0

                            for (song in shuffled) {
                                val newSum = currentSum + song.durationSeconds
                                if (abs(newSum - totalSeconds) < abs(currentSum - totalSeconds) || newSum <= totalSeconds) {
                                    currentSet.add(song)
                                    currentSum = newSum
                                }
                            }

                            val delta = abs(currentSum - totalSeconds)
                            if (delta < bestDelta) {
                                bestSet = currentSet.toList()
                                bestDelta = delta
                            }
                            if (delta == 0) break
                        }
                        bestSet.shuffled()
                    }
                    Pair(finalPlaylist, sourceFound)
                }

                // Apply results safely back to the screen state and flow triggers
                _playlistSource.value = resultInBg.second
                _generatedPlaylist.value = resultInBg.first
                _hasGenerated.value = true

            } catch (e: Exception) {
                _errorMsg.value = "Une erreur est survenue lors de la génération : ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
