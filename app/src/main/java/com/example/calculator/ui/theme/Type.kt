package com.example.calculator.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.calculator.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val AppFontFamily = FontFamily(
    Font(googleFont = GoogleFont("Inter"), fontProvider = provider)
)

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFontFamily, fontFeatureSettings = "tnum"),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFontFamily, fontFeatureSettings = "tnum"),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFontFamily, fontFeatureSettings = "tnum"),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFontFamily, fontFeatureSettings = "tnum"),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFontFamily, fontFeatureSettings = "tnum"),
    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFontFamily, fontFeatureSettings = "tnum"),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFontFamily, fontFeatureSettings = "tnum")
)