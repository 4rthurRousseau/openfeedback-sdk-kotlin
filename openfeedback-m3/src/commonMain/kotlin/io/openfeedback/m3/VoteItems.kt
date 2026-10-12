package io.openfeedback.m3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.openfeedback.ui.models.UIDot
import io.openfeedback.ui.models.UIVoteItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@ExperimentalMaterial3Api
@Composable
internal fun VoteItems(
    voteItems: ImmutableList<UIVoteItem>,
    modifier: Modifier = Modifier,
    columnCount: Int = 2,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    content: @Composable ColumnScope.(UIVoteItem) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        0.until(columnCount).forEach { column ->
            Column(
                verticalArrangement = verticalArrangement,
                modifier = Modifier.weight(1f)
            ) {
                voteItems
                    .filterIndexed { index, _ ->
                        index % columnCount == column
                    }
                    .forEach { voteItem ->
                        content(voteItem)
                    }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
internal fun VoteItemsPreview() {
    VoteItems(
        voteItems = (0..10).map {
            UIVoteItem(
                id = "",
                text = "Fun",
                dots = persistentListOf(UIDot(x = .5f, y = .5f, color = "FF00CC")),
                votedByUser = true
            )
        }.toPersistentList(),
        content = {
            VoteCard(
                voteModel = it,
                onClick = { },
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
        }
    )
}