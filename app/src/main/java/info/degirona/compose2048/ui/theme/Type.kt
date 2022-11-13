package info.degirona.compose2048.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import info.degirona.compose2048.R

val fonts = FontFamily(
    Font(R.font.clearsans_regular),
    Font(R.font.clearsans_bold, weight = FontWeight.Bold),
    Font(R.font.clearsans_light, weight = FontWeight.Light),
    Font(R.font.clearsans_medium, weight = FontWeight.Medium),
    Font(R.font.clearsans_thin, weight = FontWeight.Thin),
    Font(R.font.clearsans_italic, style = FontStyle.Italic),
    Font(R.font.clearsans_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.clearsans_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = fonts,
    h3 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp
    ),
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 24.sp,
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 12.sp,
    ),
)
