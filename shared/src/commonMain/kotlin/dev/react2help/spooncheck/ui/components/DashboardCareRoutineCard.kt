@file:Suppress("MagicNumber")

package dev.react2help.spooncheck.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.react2help.spooncheck.theme.CaptionSlateTeal
import dev.react2help.spooncheck.theme.DeepTeal
import dev.react2help.spooncheck.theme.DividerGray
import dev.react2help.spooncheck.theme.SoftTealContainer

private val CardCornerRadius = 12.dp
private val DotSize = 12.dp
private const val DefaultTotalDays = 7

/**
 * Card summarising the user's care-routine check-in streak for the week.
 *
 * Renders a row of [totalDays] dots: the first [checkedInDays] are filled in
 * [DeepTeal], the next [restDays] are filled in a lighter teal (rest days count
 * too!), and the remainder are empty ([DividerGray]).
 *
 * @param checkedInDays number of days with a completed check-in (dark dots).
 * @param restDays number of rest days counted toward the streak (medium dots).
 * @param totalDays total dot count, defaults to 7 (days in a week).
 */
@Composable
fun DashboardCareRoutineCard(
    checkedInDays: Int,
    modifier: Modifier = Modifier,
    restDays: Int = 0,
    totalDays: Int = DefaultTotalDays,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CardCornerRadius),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CareRoutineHeader()
            Text(
                text = checkInHeadline(checkedInDays),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            CheckInDots(
                checkedInDays = checkedInDays,
                restDays = restDays,
                totalDays = totalDays,
            )
            Text(
                text = "Rest Days Count Too!",
                style = MaterialTheme.typography.bodySmall,
                color = CaptionSlateTeal,
            )
        }
    }
}

@Composable
private fun CareRoutineHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Care Routine",
            style = MaterialTheme.typography.titleMedium,
        )
        FilterChip(
            selected = true,
            onClick = {},
            label = { Text("+1 Rest Day") },
            colors =
                FilterChipDefaults.filterChipColors(
                    selectedContainerColor = DeepTeal,
                    selectedLabelColor = Color.White,
                ),
        )
    }
}

@Composable
private fun CheckInDots(
    checkedInDays: Int,
    restDays: Int,
    totalDays: Int,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(totalDays) { index ->
            val dotColor =
                when {
                    index < checkedInDays -> DeepTeal
                    index < checkedInDays + restDays -> SoftTealContainer
                    else -> DividerGray
                }
            Box(
                modifier =
                    Modifier.size(DotSize)
                        .clip(CircleShape)
                        .background(dotColor),
            )
        }
    }
}

private fun checkInHeadline(count: Int): String {
    val word =
        when (count) {
            1 -> "One"
            2 -> "Two"
            3 -> "Three"
            4 -> "Four"
            5 -> "Five"
            6 -> "Six"
            7 -> "Seven"
            else -> "$count"
        }
    return "$word Check-Ins This Week"
}

@Preview(showBackground = true, widthDp = 400, heightDp = 180)
@Composable
private fun DashboardCareRoutineCardPreview() {
    DashboardCareRoutineCard(
        checkedInDays = 4,
        restDays = 1,
        totalDays = 7,
    )
}
