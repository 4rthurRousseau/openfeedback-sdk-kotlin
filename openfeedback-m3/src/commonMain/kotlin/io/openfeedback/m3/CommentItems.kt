package io.openfeedback.m3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.openfeedback.resources.LocalStrings
import io.openfeedback.ui.models.UIComment
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CommentItems(
    comments: ImmutableList<UIComment>,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    commentInput: @Composable ColumnScope.() -> Unit,
    comment: @Composable ColumnScope.(UIComment) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement
    ) {
        Text(
            text = LocalStrings.current.strings.comments.titleSection,
            style = MaterialTheme.typography.titleMedium
        )
        commentInput()
        comments.forEach { uiComment ->
            comment(uiComment)
        }
    }
}

@Composable
@Preview
internal fun CommentItemsPreview() {
    CommentItems(
        comments = persistentListOf(
            UIComment(
                id = "",
                message = "Comment preview",
                createdAt = "",
                upVotes = 10,
                dots = persistentListOf(),
                votedByUser = true,
                fromUser = false
            )
        ),
        commentInput = {
            CommentInput(value = "", onValueChange = { }, onSubmit = { })
        },
        comment = {comment ->
            Comment(comment = comment, onClick = { })
        }
    )
}