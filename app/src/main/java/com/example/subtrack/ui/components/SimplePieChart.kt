package com.example.subtrack.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min

// simple pie chart for subscription categories
@Composable
fun SimplePieChart(
    data: Map<String, Double>, // category to amount
    modifier: Modifier = Modifier,
    size: Dp = 80.dp
) {
    // colors for different categories
    val colors = listOf(
        Color(0xFF4A90E2), // blue
        Color(0xFF50C878), // green
        Color(0xFFFF6B6B), // red
        Color(0xFFFFA500), // orange
        Color(0xFF9B59B6), // purple
        Color(0xFF3498DB), // light blue
        Color(0xFFE74C3C), // darker red
        Color(0xFF1ABC9C)  // teal
    )
    
    Canvas(modifier = modifier.size(size)) {
        if (data.isEmpty()) {
            // draw empty circle if no data
            drawCircle(
                color = Color.Gray.copy(alpha = 0.3f),
                radius = this.size.minDimension / 2,
                style = Stroke(width = 8.dp.toPx())
            )
            return@Canvas
        }
        
        val total = data.values.sum()
        if (total == 0.0) return@Canvas
        
        var startAngle = -90f // start from top
        
        data.entries.forEachIndexed { index, entry ->
            val sweepAngle = (entry.value / total * 360f).toFloat()
            val color = colors[index % colors.size]
            
            // draw arc for this category
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(0f, 0f),
                size = Size(this.size.minDimension, this.size.minDimension)
            )
            
            startAngle += sweepAngle
        }
        
        // draw white circle in center for donut effect
        val centerRadius = this.size.minDimension / 3f
        drawCircle(
            color = Color.White,
            radius = centerRadius
        )
    }
}
