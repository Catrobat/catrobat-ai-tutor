package org.catrobat.aitutor.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import org.catrobat.aitutor.ui.theme.AiTutorTheme
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.ask_your_question
import org.catrobat.shared.generated.resources.choose_your_ai
import org.catrobat.shared.generated.resources.copy_and_paste
import org.catrobat.shared.generated.resources.got_it
import org.catrobat.shared.generated.resources.launch_and_learn
import org.catrobat.shared.generated.resources.next
import org.catrobat.shared.generated.resources.skip
import org.catrobat.shared.generated.resources.tutorial_header
import org.catrobat.shared.generated.resources.tutorial_step_1
import org.catrobat.shared.generated.resources.tutorial_step_1_description
import org.catrobat.shared.generated.resources.tutorial_step_2
import org.catrobat.shared.generated.resources.tutorial_step_2_description
import org.catrobat.shared.generated.resources.tutorial_step_3
import org.catrobat.shared.generated.resources.tutorial_step_3_description
import org.catrobat.shared.generated.resources.tutorial_step_4
import org.catrobat.shared.generated.resources.tutorial_step_4_description
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private data class TutorialPageData(
    val imageResource: DrawableResource,
    val title: StringResource,
    val description: StringResource,
)

private val tutorialPages =
    listOf(
        TutorialPageData(
            imageResource = Res.drawable.ask_your_question,
            title = Res.string.tutorial_step_1,
            description = Res.string.tutorial_step_1_description,
        ),
        TutorialPageData(
            imageResource = Res.drawable.choose_your_ai,
            title = Res.string.tutorial_step_2,
            description = Res.string.tutorial_step_2_description,
        ),
        TutorialPageData(
            imageResource = Res.drawable.launch_and_learn,
            title = Res.string.tutorial_step_3,
            description = Res.string.tutorial_step_3_description,
        ),
        TutorialPageData(
            imageResource = Res.drawable.copy_and_paste,
            title = Res.string.tutorial_step_4,
            description = Res.string.tutorial_step_4_description,
        ),
    )

@Composable
internal fun TutorialView(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier =
                Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .widthIn(max = 580.dp),
            color = AiTutorTheme.colors.surface.copy(alpha = 0.95f),
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 8.dp,
        ) {
            TutorialPagerContent(
                pages = tutorialPages,
                onDismissRequest = onDismissRequest,
            )
        }
    }
}

@Composable
private fun TutorialPagerContent(
    pages: List<TutorialPageData>,
    onDismissRequest: () -> Unit,
) {
    val pagerState = rememberPagerState { pages.size }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.tutorial_header),
            style = MaterialTheme.typography.headlineSmall,
            color = AiTutorTheme.colors.onSurface,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(24.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.defaultMinSize(minHeight = 300.dp),
        ) { pageIndex ->
            TutorialPage(data = pages[pageIndex])
        }

        Spacer(Modifier.height(24.dp))

        PagerIndicator(
            pagerState = pagerState,
        )

        Spacer(Modifier.height(24.dp))

        PagerButtons(
            pagerState = pagerState,
            onNext = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            },
            onDone = onDismissRequest,
        )
    }
}

@Composable
private fun TutorialPage(data: TutorialPageData) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(resource = data.imageResource),
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Fit,
        )
        Text(
            text = stringResource(data.title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = AiTutorTheme.colors.onSurface,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(data.description),
            style = MaterialTheme.typography.bodyMedium,
            color = AiTutorTheme.colors.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
    }
}

@Composable
private fun PagerIndicator(pagerState: PagerState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color by animateColorAsState(
                targetValue =
                    if (pagerState.currentPage == iteration) {
                        AiTutorTheme.colors.primary
                    } else {
                        AiTutorTheme.colors.primary.copy(
                            alpha = 0.3f,
                        )
                    },
                label = "IndicatorColor",
            )
            Box(
                modifier =
                    Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp),
            )
        }
    }
}

@Composable
private fun PagerButtons(
    pagerState: PagerState,
    onNext: () -> Unit,
    onDone: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val isLastPage = pagerState.currentPage == pagerState.pageCount - 1

        // "Skip" button appears on all but the last page
        if (isLastPage) {
            Spacer(modifier = Modifier.width(1.dp)) // To balance the Row
        } else {
            TextButton(onClick = onDone) {
                Text(text = stringResource(Res.string.skip), color = AiTutorTheme.colors.primary)
            }
        }

        // "Next" or "Got It!" button
        Button(
            onClick = if (isLastPage) onDone else onNext,
            colors = ButtonDefaults.buttonColors(containerColor = AiTutorTheme.colors.primary),
        ) {
            Text(
                text = stringResource(if (isLastPage) Res.string.got_it else Res.string.next),
                color = AiTutorTheme.colors.onPrimary,
            )
        }
    }
}
