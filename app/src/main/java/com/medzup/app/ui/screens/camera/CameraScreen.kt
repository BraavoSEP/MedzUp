package com.medzup.app.ui.screens.camera

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.concurrent.Executor

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(navController: NavController) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val scanMode = navController.currentBackStackEntry?.arguments?.getString("scanMode") ?: "box_name"
    
    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status.isGranted) {
        CameraView(context = context, navController = navController, scanMode = scanMode)
    } else {
        // Handle permission denial, show a message to the user
    }
}

@Composable
fun CameraView(context: Context, navController: NavController, scanMode: String) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val executor = ContextCompat.getMainExecutor(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    imageCapture = ImageCapture.Builder().build()

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageCapture
                        )
                    } catch (e: Exception) {
                        Log.e("CameraScreen", "Use case binding failed", e)
                    }
                }, executor)
                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .size(80.dp)
                .border(2.dp, Color.White, CircleShape),
            onClick = {
                takePicture(context, imageCapture, navController, scanMode)
            }
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Take picture",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

private fun takePicture(
    context: Context,
    imageCapture: ImageCapture?,
    navController: NavController,
    scanMode: String
) {
    if (imageCapture == null) return

    val executor = ContextCompat.getMainExecutor(context)
    imageCapture.takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: androidx.camera.core.ImageProxy) {
            processImage(image, navController, scanMode)
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e("CameraScreen", "Image capture failed", exception)
        }
    })
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
private fun processImage(imageProxy: androidx.camera.core.ImageProxy, navController: NavController, scanMode: String) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val cleanedText = visionText.text.replace("\n", " ").trim()
                Log.d("CameraScreen", "Recognized Text: $cleanedText")
                
                // Pass the recognized text back to the appropriate screen
                val key = if (scanMode == "bula") "bula_text" else "ocr_text"
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(key, cleanedText)
                navController.popBackStack()
                
                imageProxy.close()
            }
            .addOnFailureListener { e ->
                Log.e("CameraScreen", "Text recognition failed", e)
                imageProxy.close()
            }
    }
}
// The takePicture and text recognition logic will be added next 