package org.catrobat.aitutor.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.mikepenz.aboutlibraries.ui.compose.LibraryDefaults
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.m3.chipColors
import com.mikepenz.aboutlibraries.ui.compose.m3.libraryColors
import com.mikepenz.aboutlibraries.ui.compose.rememberLibraries
import org.catrobat.aitutor.ui.theme.AiTutorColors
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.catrobat_ai_tutor
import org.catrobat.shared.generated.resources.close
import org.catrobat.shared.generated.resources.developers
import org.catrobat.shared.generated.resources.logo_catrobat
import org.catrobat.shared.generated.resources.open_source_libraries
import org.catrobat.shared.generated.resources.source_code
import org.catrobat.shared.generated.resources.version
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun AboutScreen(onDismissRequest: () -> Unit) {
    val libraries by rememberLibraries {
        Res.readBytes("files/aboutlibraries.json").decodeToString()
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Scaffold(
            containerColor = AiTutorColors.surface,
            topBar = {
                TopAppBar(
                    title = { /* No title needed */ },
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                        ),
                    actions = {
                        IconButton(onClick = onDismissRequest) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(Res.string.close),
                                tint = AiTutorColors.onSurfaceVariant,
                            )
                        }
                    },
                )
            },
        ) { paddingValues ->
            LibrariesContainer(
                libraries = libraries,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                colors =
                    LibraryDefaults.libraryColors(
                        backgroundColor = AiTutorColors.surface,
                        contentColor = AiTutorColors.onSurface,
                        dialogConfirmButtonColor = AiTutorColors.primary,
                        licenseChipColors =
                            LibraryDefaults.chipColors(
                                containerColor = AiTutorColors.primary,
                                contentColor = AiTutorColors.onPrimary,
                            ),
                        versionChipColors =
                            LibraryDefaults.chipColors(
                                containerColor = AiTutorColors.secondaryContainer,
                                contentColor = AiTutorColors.onSecondaryContainer,
                            ),
                    ),
                divider = {
                    HorizontalDivider()
                },
                header = {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.logo_catrobat),
                                contentDescription = null,
                                modifier =
                                    Modifier
                                        .size(64.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = stringResource(Res.string.catrobat_ai_tutor),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = AiTutorColors.onSurface,
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = stringResource(Res.string.version, "0.0.1"),
                                fontSize = 16.sp,
                                color = AiTutorColors.onSurfaceVariant,
                            )
                            Spacer(Modifier.height(24.dp))
                        }
                    }

                    // Developers Section
                    item {
                        SectionHeader(title = stringResource(Res.string.developers))
                    }
                    item {
                        DeveloperInfoCard(
                            "Muhammad Haris Sabil Al Karim",
                            "https://github.com/harissabil",
                            "https://avatars.githubusercontent.com/u/97924751?v=4",
                        )
                    }
                    item {
                        DeveloperInfoCard(
                            "Paul Spiesberger",
                            "https://github.com/spipau",
                            "https://avatars.githubusercontent.com/u/2797845?v=4",
                        )
                    }

                    // Source Code Section
                    item {
                        Spacer(Modifier.height(16.dp))
                        SectionHeader(title = stringResource(Res.string.source_code))
                        LinkCard(
                            "GitHub Repository",
                            "https://github.com/Catrobat/catrobat-ai-tutor",
                            Icons.Default.Code,
                        )
                        Spacer(Modifier.height(16.dp))
                    }

                    // Header for the actual libraries list
                    item {
                        SectionHeader(title = stringResource(Res.string.open_source_libraries))
                        Spacer(Modifier.height(8.dp))
                    }
                },
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = AiTutorColors.onSurfaceVariant,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
    )
}

@Composable
private fun DeveloperInfoCard(
    name: String,
    githubUrl: String,
    profileUrl: String,
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = AiTutorColors.secondaryContainer,
        modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = profileUrl,
                contentDescription = null,
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape),
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = name,
                    fontWeight = FontWeight.SemiBold,
                    color = AiTutorColors.onSecondaryContainer,
                )
                Text(text = githubUrl, fontSize = 14.sp, color = AiTutorColors.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun LinkCard(
    title: String,
    url: String,
    icon: ImageVector,
) {
    val uriHandler = LocalUriHandler.current
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = AiTutorColors.secondaryContainer,
        modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
        onClick = { uriHandler.openUri(url) },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AiTutorColors.onSecondaryContainer,
            )
            Spacer(Modifier.width(16.dp))
            Text(text = title, color = AiTutorColors.onSecondaryContainer)
        }
    }
}
