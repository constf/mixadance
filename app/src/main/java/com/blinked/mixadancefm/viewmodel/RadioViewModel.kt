package com.blinked.mixadancefm.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

const val SAMUI_MUSIC_URL: String = "https://stream.mixadance.fm/radiosamui"
const val MIXADANCE_URL: String = "https://stream.mixadance.fm/mixadance"
const val RELAX_URL: String = "https://stream.mixadance.fm/relax#.m4a"
const val FITNESS_URL: String = "https://stream.mixadance.fm/fitness"
const val JAZZ_URL: String = "https://stream.mixadance.fm/mixadancejazz"


class RadioViewModel(private val inApplication: Application): AndroidViewModel(inApplication) {

    private val exoPlayer: ExoPlayer = getPlayerInstance(inApplication).also { it.addListener(SamuiDataAndErrorListener()) }

    private var _errorCode: MutableLiveData<String> = MutableLiveData(null)
    val errorCode: LiveData<String> get() = _errorCode

    private var _metaData: MutableLiveData<String> = MutableLiveData(_musicData)
    val metaData: LiveData<String> get() = _metaData

    fun isPlayingNow(): Boolean {
        return exoPlayer.isPlaying
    }

    fun clearMusicData() {
        _metaData.value = "   "
    }

    fun preparePlayer(url: String) {
        val uri = Uri.parse(url)

        try {
            val mediaItem: MediaItem = MediaItem.fromUri(uri)

            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.playWhenReady = false
            exoPlayer.prepare()
        } catch (e: Exception) {
            Log.d("Player initialize error", e.message.toString())
        }

    }

    fun Play() {
        if (exoPlayer.isPlaying) return

        exoPlayer.play()
    }

    fun Pause() {
        if(!exoPlayer.isPlaying) return
        exoPlayer.pause()
    }

    fun Stop() {
        exoPlayer.stop()
    }

    fun clearMedia() {
        exoPlayer.clearMediaItems()
    }

    fun ReleasePlayer() {
        exoPlayer.release()
    }


    inner class SamuiDataAndErrorListener: Player.Listener {
        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            _errorCode.value = PlaybackException.getErrorCodeName(error.errorCode)
            Pause()
        }

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            super.onMediaMetadataChanged(mediaMetadata)
            _metaData.value = with(mediaMetadata) {
                val str: String =
                    if (!title.isNullOrEmpty()){ title.toString() }
                    else if (!station.isNullOrEmpty()) { station.toString() }
                    else { "   " }

                str + "   " + str + "   " + str + "   " + str + "   " + str + "   " + str + "   " + str + "   "
            }
        }
    }

    companion object {
        private var instance: ExoPlayer? = null

        var _musicData: String? = "          "

        fun getPlayerInstance(context: Context): ExoPlayer {
            return instance ?: synchronized(this){
                instance ?: buildPlayer(context).also{ instance = it}
            }
        }

        private fun buildPlayer(context: Context): ExoPlayer {
            return ExoPlayer.Builder(context).build()
        }
    }
}
