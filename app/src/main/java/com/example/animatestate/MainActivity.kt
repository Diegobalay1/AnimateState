package com.example.animatestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.core.Spring.DampingRatioHighBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animatestate.ui.theme.AnimateStateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimateStateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RotationDemo()
                }
            }
        }
    }
}

/**
 * Enumeracion para los colores
 */
enum class BoxColor {
    Red, Magenta
}

@Composable
fun ColorChangeDemo() {
    var colorState by remember { mutableStateOf(BoxColor.Red) }

    val animatedColor: Color by animateColorAsState(
        targetValue = when (colorState) {
            BoxColor.Red -> Color.Magenta
            BoxColor.Magenta -> Color.Red
        },
        animationSpec = tween(4500)
    )
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier
            .padding(20.dp)
            .size(200.dp)
            //.background(Color.Red)
            .background(animatedColor)
        )
        Button(onClick = { 
            colorState = when (colorState) {
                BoxColor.Red -> BoxColor.Magenta
                BoxColor.Magenta -> BoxColor.Red
            }
        },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Change Color")
        }
    }
}

@Preview
@Composable
fun ColorChangePreview() {
    AnimateStateTheme {
        ColorChangeDemo()
    }
}

/**
 * Enumeración para el movimiento
 */
enum class BoxPosition {
    Start, End
}

@Composable
fun MotionDemo() {
    val screenWidth = (LocalConfiguration.current.screenWidthDp.dp)
    var boxState by remember { mutableStateOf(BoxPosition.Start) }
    val boxSideLength = 70.dp

    val animatedOffset: Dp by animateDpAsState(
        targetValue = when (boxState) {
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> screenWidth - boxSideLength
        },
        //animationSpec = tween(durationMillis = 500)
        /*animationSpec = spring(
            dampingRatio = DampingRatioHighBouncy, stiffness = StiffnessVeryLow
        ),*/
        animationSpec = keyframes {
            durationMillis = 1000
            100.dp.at(10).with(LinearEasing)
            110.dp.at(500).with(FastOutSlowInEasing)
            200.dp.at(700).with(LinearOutSlowInEasing)
        }
    )
    
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            //.offset(x = 0.dp, y = 20.dp)
            .offset(x = animatedOffset, y = 20.dp)
            .size(boxSideLength)
            .background(Color.Red)
        )
        
        Spacer(modifier = Modifier.height(50.dp))
        
        Button(onClick = {
            boxState = when(boxState) {
                BoxPosition.Start -> BoxPosition.End
                BoxPosition.End -> BoxPosition.Start
            }
        },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Move Box")
        }
    }
}

@Preview
@Composable
fun MotionDemoPreview() {
    AnimateStateTheme {
        MotionDemo()
    }
}


/**
 * Animating rotation with animateFloatAsState
 */
@Composable
fun RotationDemo() {
    var rotated by remember { mutableStateOf(false) }
    
    val angle by animateFloatAsState(
        targetValue = if (rotated) 360f else 0f,
        //animationSpec = tween(durationMillis = 2500)
        animationSpec = tween(durationMillis = 2500, easing = LinearEasing)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.propeller),
            contentDescription = "fan",
            modifier = Modifier
                .rotate(angle)
                .padding(10.dp)
                .size(300.dp)
        )
        Button(
            onClick = { rotated = !rotated },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Rotate Propeller")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnimateStateTheme {
        RotationDemo()
    }
}












