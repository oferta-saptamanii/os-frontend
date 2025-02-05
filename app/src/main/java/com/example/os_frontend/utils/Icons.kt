package com.example.os_frontend.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Shop: ImageVector
    get() {
        if (_Shop != null) {
            return _Shop!!
        }
        _Shop = ImageVector.Builder(
            name = "Shop",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(2.97f, 1.35f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3.73f, 1f)
                horizontalLineToRelative(8.54f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.76f, 0.35f)
                lineToRelative(2.609f, 3.044f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 16f, 5.37f)
                verticalLineToRelative(0.255f)
                arcToRelative(2.375f, 2.375f, 0f, isMoreThanHalf = false, isPositiveArc = true, -4.25f, 1.458f)
                arcTo(2.37f, 2.37f, 0f, isMoreThanHalf = false, isPositiveArc = true, 9.875f, 8f)
                arcTo(2.37f, 2.37f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8f, 7.083f)
                arcTo(2.37f, 2.37f, 0f, isMoreThanHalf = false, isPositiveArc = true, 6.125f, 8f)
                arcToRelative(2.37f, 2.37f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.875f, -0.917f)
                arcTo(2.375f, 2.375f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 5.625f)
                verticalLineTo(5.37f)
                arcToRelative(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.361f, -0.976f)
                close()
                moveToRelative(1.78f, 4.275f)
                arcToRelative(1.375f, 1.375f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.75f, 0f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 0f)
                arcToRelative(1.375f, 1.375f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.75f, 0f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 0f)
                arcToRelative(1.375f, 1.375f, 0f, isMoreThanHalf = true, isPositiveArc = false, 2.75f, 0f)
                verticalLineTo(5.37f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.12f, -0.325f)
                lineTo(12.27f, 2f)
                horizontalLineTo(3.73f)
                lineTo(1.12f, 5.045f)
                arcTo(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1f, 5.37f)
                verticalLineToRelative(0.255f)
                arcToRelative(1.375f, 1.375f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.75f, 0f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 0f)
                moveTo(1.5f, 8.5f)
                arcTo(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 9f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(1f)
                verticalLineToRelative(-5f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, -1f)
                horizontalLineToRelative(3f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 1f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(6f)
                verticalLineTo(9f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 0f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(0.5f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 1f)
                horizontalLineTo(0.5f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -1f)
                horizontalLineTo(1f)
                verticalLineTo(9f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.5f, -0.5f)
                moveTo(4f, 15f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(-5f)
                horizontalLineTo(4f)
                close()
                moveToRelative(5f, -5f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, -1f)
                horizontalLineToRelative(2f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 1f)
                verticalLineToRelative(3f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, 1f)
                horizontalLineToRelative(-2f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, -1f)
                close()
                moveToRelative(3f, 0f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(2f)
                close()
            }
        }.build()
        return _Shop!!
    }

private var _Shop: ImageVector? = null



public val Menu_book: ImageVector
    get() {
        if (_Menu_book != null) {
            return _Menu_book!!
        }
        _Menu_book = ImageVector.Builder(
            name = "Menu_book",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(560f, 396f)
                verticalLineToRelative(-68f)
                quadToRelative(33f, -14f, 67.5f, -21f)
                reflectiveQuadToRelative(72.5f, -7f)
                quadToRelative(26f, 0f, 51f, 4f)
                reflectiveQuadToRelative(49f, 10f)
                verticalLineToRelative(64f)
                quadToRelative(-24f, -9f, -48.5f, -13.5f)
                reflectiveQuadTo(700f, 360f)
                quadToRelative(-38f, 0f, -73f, 9.5f)
                reflectiveQuadTo(560f, 396f)
                moveToRelative(0f, 220f)
                verticalLineToRelative(-68f)
                quadToRelative(33f, -14f, 67.5f, -21f)
                reflectiveQuadToRelative(72.5f, -7f)
                quadToRelative(26f, 0f, 51f, 4f)
                reflectiveQuadToRelative(49f, 10f)
                verticalLineToRelative(64f)
                quadToRelative(-24f, -9f, -48.5f, -13.5f)
                reflectiveQuadTo(700f, 580f)
                quadToRelative(-38f, 0f, -73f, 9f)
                reflectiveQuadToRelative(-67f, 27f)
                moveToRelative(0f, -110f)
                verticalLineToRelative(-68f)
                quadToRelative(33f, -14f, 67.5f, -21f)
                reflectiveQuadToRelative(72.5f, -7f)
                quadToRelative(26f, 0f, 51f, 4f)
                reflectiveQuadToRelative(49f, 10f)
                verticalLineToRelative(64f)
                quadToRelative(-24f, -9f, -48.5f, -13.5f)
                reflectiveQuadTo(700f, 470f)
                quadToRelative(-38f, 0f, -73f, 9.5f)
                reflectiveQuadTo(560f, 506f)
                moveTo(260f, 640f)
                quadToRelative(47f, 0f, 91.5f, 10.5f)
                reflectiveQuadTo(440f, 682f)
                verticalLineToRelative(-394f)
                quadToRelative(-41f, -24f, -87f, -36f)
                reflectiveQuadToRelative(-93f, -12f)
                quadToRelative(-36f, 0f, -71.5f, 7f)
                reflectiveQuadTo(120f, 268f)
                verticalLineToRelative(396f)
                quadToRelative(35f, -12f, 69.5f, -18f)
                reflectiveQuadToRelative(70.5f, -6f)
                moveToRelative(260f, 42f)
                quadToRelative(44f, -21f, 88.5f, -31.5f)
                reflectiveQuadTo(700f, 640f)
                quadToRelative(36f, 0f, 70.5f, 6f)
                reflectiveQuadToRelative(69.5f, 18f)
                verticalLineToRelative(-396f)
                quadToRelative(-33f, -14f, -68.5f, -21f)
                reflectiveQuadToRelative(-71.5f, -7f)
                quadToRelative(-47f, 0f, -93f, 12f)
                reflectiveQuadToRelative(-87f, 36f)
                close()
                moveToRelative(-40f, 118f)
                quadToRelative(-48f, -38f, -104f, -59f)
                reflectiveQuadToRelative(-116f, -21f)
                quadToRelative(-42f, 0f, -82.5f, 11f)
                reflectiveQuadTo(100f, 762f)
                quadToRelative(-21f, 11f, -40.5f, -1f)
                reflectiveQuadTo(40f, 726f)
                verticalLineToRelative(-482f)
                quadToRelative(0f, -11f, 5.5f, -21f)
                reflectiveQuadTo(62f, 208f)
                quadToRelative(46f, -24f, 96f, -36f)
                reflectiveQuadToRelative(102f, -12f)
                quadToRelative(58f, 0f, 113.5f, 15f)
                reflectiveQuadTo(480f, 220f)
                quadToRelative(51f, -30f, 106.5f, -45f)
                reflectiveQuadTo(700f, 160f)
                quadToRelative(52f, 0f, 102f, 12f)
                reflectiveQuadToRelative(96f, 36f)
                quadToRelative(11f, 5f, 16.5f, 15f)
                reflectiveQuadToRelative(5.5f, 21f)
                verticalLineToRelative(482f)
                quadToRelative(0f, 23f, -19.5f, 35f)
                reflectiveQuadToRelative(-40.5f, 1f)
                quadToRelative(-37f, -20f, -77.5f, -31f)
                reflectiveQuadTo(700f, 720f)
                quadToRelative(-60f, 0f, -116f, 21f)
                reflectiveQuadToRelative(-104f, 59f)
                moveTo(280f, 466f)
            }
        }.build()
        return _Menu_book!!
    }

private var _Menu_book: ImageVector? = null
