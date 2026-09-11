package dev.react2help.spooncheck.ui.components

import dev.react2help.spooncheck.modelsandstate.Task

data class SectionData(val sectionTitle: String, val sectionItems: List<Task>)
/*
@Composable
fun ListOfTaskCards(
    tasks: List<Task>,
    OnTransferred: (Long) -> Unit,
    modifier: Modifier = Modifier
) {}

@Composable
fun ListOfSctions(sections: List<SectionData>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        sections.forEachIndexed { index, sectionData -> section(sectionData) }
    }
}



fun LazyListScope.section(sectionData: SectionData) {
    item {
        Card(elevation = CardDefaults.cardElevation(8.dp), modifier = Modifier.fillMaxSize()) {
            // Comvert Category name to a string then change the first character to uppercase,
            // all other to lowercase
            Text(
                text = sectionData.sectionTitle.lowercase().replaceFirstChar { it.uppercase() },
                modifier = Modifier.padding(16.dp)
            )
            sectionData.sectionItems.forEach {
                if (it.priority.ordinal > Priority.medium.ordinal) {
                    SectionItem(it, modifier = Modifier.padding(2.dp).fillMaxHeight())
                }
            }
        }
        Spacer(modifier = Modifier.size(5.dp))
    }
}

 */
