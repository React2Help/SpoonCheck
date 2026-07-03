package dev.react2help.spooncheck.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import spooncheck.shared.generated.resources.Res
import spooncheck.shared.generated.resources.spoon
import spooncheck.shared.generated.resources.schedule_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.stat_0_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.stat_1_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.stat_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.stat_3_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24
import spooncheck.shared.generated.resources.calendar_month_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24


@Composable
    fun TaskListScreen(viewModel: TaskListViewModel){
    }
@Preview
@Composable
fun TaskCard(){
    Card(
        modifier = Modifier
            .size(width = 240.dp, height = 100.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {
            Column {
                Text(
                    "Title",
                    modifier = Modifier
                        .padding(8.dp),
                    fontWeight = FontWeight.W900
                )
                Text(
                    "Description",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            RHSTaskCard()
        }

    }

}
@Preview
@Composable
fun RHSTaskCard(){
    Column(
        Modifier.background(Color.LightGray)
            .fillMaxHeight()
            .padding(5.dp)
    ) {
        Row {
            Icon(
                painter = painterResource(Res.drawable.spoon),
                contentDescription = "Spoon icon",
                Modifier.size(18.dp, 18.dp)
            )
            Text("5")
        }
        Row {
            Icon(

                contentDescription = "low priority symbol",
                painter = painterResource(Res.drawable.stat_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24),
                modifier = Modifier.size(18.dp, 18.dp)
            )
            Text("High")
        }
        Row {
            Icon(
                modifier = Modifier.size(18.dp, 18.dp),
                contentDescription = "Clock Symbol",
                painter = painterResource(Res.drawable.schedule_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24)
            )
            Text("12:30PM")
        }
        Row {
            Icon(
                modifier = Modifier.size(18.dp, 18.dp),
                contentDescription = "Calendar Symbol",
                painter = painterResource(Res.drawable.calendar_month_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24)
            )
            Text("06/07/2026")
        }
    }

}