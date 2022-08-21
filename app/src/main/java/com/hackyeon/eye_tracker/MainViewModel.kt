package com.hackyeon.eye_tracker

import android.Manifest
import android.os.Build
import androidx.camera.core.CameraSelector
import androidx.camera.video.*
import androidx.core.util.Consumer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

class MainViewModel: ViewModel() {

    //////////////////////////////////////////////
    ////////////// for navigation ////////////////
    //////////////////////////////////////////////
    private val _navigationCommand: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val navigationCommand: LiveData<NavigationCommand> = _navigationCommand
    fun navigate(directions: NavDirections) {
        _navigationCommand.postValue(NavigationCommand.ToDirection(directions))
    }


    companion object {
        const val DEFAULT_ANIMATION_SPEED = 1
        const val DEFAULT_FLASH_TIME = 1
    }


    val animationSpeed = MutableLiveData<Int>()
    val flashTime = MutableLiveData<Int>()




    //////////////////////////////////////////////
    //////////////// for camera //////////////////
    //////////////////////////////////////////////
    val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    var qualityList: List<Quality> = emptyList()

    var videoCapture: VideoCapture<Recorder>? = null

    var currentRecording: Recording? = null

    var recordingState: VideoRecordEvent? = null

    val captureListener = Consumer<VideoRecordEvent> { event ->
        if(event !is VideoRecordEvent.Status) recordingState = event

    }

    fun stopRecording() {
        if(currentRecording == null || recordingState is VideoRecordEvent.Finalize) {
            return
        }
        currentRecording?.stop()
        currentRecording = null
    }

    /**
     * 녹화를 하기위한 권한을 가져온다
     * @return Array<String>
     */
    fun getPermissionRequired(): Array<String> {
        val permissionList = mutableListOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        return permissionList.toTypedArray()
    }

}
