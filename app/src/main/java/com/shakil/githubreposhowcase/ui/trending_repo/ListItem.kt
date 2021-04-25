package com.shakil.githubreposhowcase.ui.trending_repo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.shakil.githubreposhowcase.domain.model.TrendingRepo
import com.shakil.githubreposhowcase.ui.theme.placeHolderGradient

@Composable
fun ListItem(trendingRepo: TrendingRepo, onTap: () -> Unit) {

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().clickable { trendingRepo.isExpanded.value = !trendingRepo.isExpanded.value }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(trendingRepo.imageUrl)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = trendingRepo.username,
                    style = TextStyle(color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f))
                )
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = trendingRepo.libraryName, style = TextStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 20.sp
                    )
                )

                if (trendingRepo.isExpanded.value) {
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = trendingRepo.description,
                        style = TextStyle(color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f))
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp),verticalAlignment = Alignment.CenterVertically) {
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(trendingRepo.color)
                        )
                        Text(
                            trendingRepo.language,
                            style = TextStyle(color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f))
                        )
                        Spacer(
                            modifier = Modifier
                                .size(20.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp).padding(end = 8.dp),
                            tint = Color.Yellow
                        )
                        Text(
                            trendingRepo.stars,
                            style = TextStyle(color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f))
                        )
                    }
                }
            }

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(start = 32.dp)
                .background(Color.LightGray.copy(alpha = 0.5f))
        )
    }

}

@Composable
fun Avatar(imageUrl: String) {
    CoilImage(
        contentDescription = null,
        modifier = Modifier.padding(16.dp).size(40.dp),
        contentScale = ContentScale.Fit,
        data = imageUrl,
        fadeIn = true,
        requestBuilder = {
            transformations(CircleCropTransformation())
        },
        loading = {
            Spacer(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(placeHolderGradient)
            )
        },
        error = {
            Spacer(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(placeHolderGradient)
            )
        })
}