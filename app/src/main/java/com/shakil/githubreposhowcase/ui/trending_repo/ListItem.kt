package com.shakil.githubreposhowcase.ui.trending_repo

import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.layout.ContentScale.Companion.Fit
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.rememberCoilPainter
import com.shakil.githubreposhowcase.domain.model.TrendingRepo
import com.shakil.githubreposhowcase.ui.theme.placeHolderGradient

@Composable
fun ListItem(trendingRepo: TrendingRepo, onTap: () -> Unit) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { trendingRepo.isExpanded.value = !trendingRepo.isExpanded.value }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(trendingRepo.imageUrl)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = trendingRepo.username,
                    style = TextStyle(
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                        fontSize = 18.sp
                    )
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

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
                            modifier = Modifier
                                .size(32.dp)
                                .padding(end = 8.dp),
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
                .height(1.dp)
                .padding(start = 32.dp)
                .background(Color.LightGray.copy(alpha = 0.5f))
        )
    }

}

@Composable
fun Avatar(imageUrl: String) {

    Image(
        modifier = Modifier
            .padding(16.dp)
            .size(40.dp),
        painter = rememberCoilPainter(
            previewPlaceholder = R.drawable.notification_action_background,
            request = imageUrl,
            fadeIn = true,
            requestBuilder = {
                scale(Scale.FIT)
                transformations(CircleCropTransformation())
            },
        ),
        contentDescription = null,
    )

    /*CoilImage(
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
        })*/
}


//Box(modifier = modifier.graphicsLayer {
//        alpha = alphaFraction ?: 1f
//    }) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .padding(
//                    top = 16.dp,
//                    bottom = 16.dp,
//                    start = Padding16dp,
//                    end = Padding16dp
//                ),
//            shape = RoundedCornerShape(12.dp),
//            elevation = 8.dp,
//            backgroundColor = MaterialTheme.colors.background,
//        ) {
//
//            ConstraintLayout(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(Padding8dp)
//            ) {
//                val (image1, image2, teamName1, teamName2, score, datetime, place) = createRefs()
//
//                val centerVerticleGuidline = createGuidelineFromTop(0.4f)
//                val centerHorizontalGuidline = createGuidelineFromStart(0.5f)
//
//                FACRImageOrInitials(
//                    modifier = Modifier
//                        .requiredSize(imageSize)
//                        .constrainAs(image1) {
//                            start.linkTo(parent.start)
//                            end.linkTo(centerHorizontalGuidline, Padding50dp)
//                            top.linkTo(centerVerticleGuidline)
//                            bottom.linkTo(centerVerticleGuidline)
//                        },
//                    photoUrl = homeTeamPhotoUrl, name = homeTeamName,
//                    textStyle = facrNameIntial2Text()
//                )
//
//                Text(
//                    text = homeTeamName,
//                    style = facrBold16BodyText(),
//                    color = MaterialTheme.colors.onBackground,
//                    maxLines = 2,
//                    textAlign = TextAlign.Center,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier
//                        .constrainAs(teamName1) {
//                            start.linkTo(parent.start)
//                            end.linkTo(centerHorizontalGuidline, Padding50dp)
//                            top.linkTo(image1.bottom, Padding8dp)
//                            bottom.linkTo(parent.bottom, Padding8dp)
//                            width = Dimension.fillToConstraints
//                        }
//                )
//
//                FACRImageOrInitials(
//                    modifier = Modifier
//                        .requiredSize(imageSize)
//                        .constrainAs(image2) {
//                            end.linkTo(parent.end)
//                            start.linkTo(centerHorizontalGuidline, Padding50dp)
//                            top.linkTo(centerVerticleGuidline)
//                            bottom.linkTo(centerVerticleGuidline)
//                        },
//                    photoUrl = guestTeamPhotoUrl, name = guestTeamName,
//                    textStyle = facrNameIntial2Text()
//                )
//
//                Text(
//                    text = guestTeamName,
//                    style = facrBold16BodyText(),
//                    color = MaterialTheme.colors.onBackground,
//                    maxLines = 2,
//                    textAlign = TextAlign.Center,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier
//                        .constrainAs(teamName2) {
//                            start.linkTo(centerHorizontalGuidline, Padding50dp)
//                            end.linkTo(parent.end)
//                            top.linkTo(image2.bottom, Padding8dp)
//                            bottom.linkTo(parent.bottom, Padding8dp)
//                            width = Dimension.fillToConstraints
//                        }
//                )
//
//                Text(
//                    text = date,
//                    style = facrBold20Text(),
//                    maxLines = 1,
//                    modifier = Modifier.constrainAs(score) {
//                        centerVerticallyTo(image1)
//                        start.linkTo(image1.end)
//                        end.linkTo(image2.start)
//                    }
//                )
//
//                Text(
//                    text = "${match.playing_time} ${location}",
//                    style = facrNormal14SubTitleText(),
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier.constrainAs(place) {
//                        top.linkTo(score.bottom)
//                        start.linkTo(teamName1.end,2.dp)
//                        end.linkTo(teamName2.start,2.dp)
//                        width = Dimension.fillToConstraints
//                    }
//                )
//            }
//        }
//        if (showEventIcon)
//            Card(
//                shape = CircleShape,
//                elevation = 8.dp,
//                backgroundColor = if (isMatchEventAdded)
//                    MaterialTheme.colors.primary else MaterialTheme.colors.background,
//                modifier = Modifier
//                    .requiredSize(50.dp)
//                    .clickable(onClick = {
//                        onEventClicked?.invoke(match)
//                    })
//                    .align(Alignment.BottomCenter)
//
//            ) {
//                Icon(
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .wrapContentSize(Alignment.Center),
//                    tint = if (isMatchEventAdded)
//                        MaterialTheme.colors.background else MaterialTheme.colors.primary,
//                    imageVector = Icons.Default.Notifications
//                )
//            }
//
//    }

@Preview
@Composable
fun DemoLayout() {

    Card(
        modifier = Modifier
                .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.background,
    ) {

        CustomLayout(modifier = Modifier
            .fillMaxSize()
        ) {

            Spacer(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Red)

            )

            Spacer(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Green)
            )

            Text(
                modifier = Modifier
                    ,
                    text = "2:2",
                    style = TextStyle(fontSize = 22.sp,textAlign = TextAlign.Center),
                    maxLines = 1
                )

            Text(
                modifier = Modifier
                    ,
                text = "Eden shhhhshshshshsshh",
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontSize = 18.sp,textAlign = TextAlign.Center),
                maxLines = 1
            )

            Text(
                modifier = Modifier
                   ,
                text = "22:10 - 22:33",
                style = TextStyle(fontSize = 16.sp,textAlign = TextAlign.Center),
                maxLines = 1
            )

            Text(
                modifier = Modifier,
                    //.background(Color.Cyan),
                text = "SK Slavisdf",
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp),
                maxLines = 1
            )

            Text(
                modifier = Modifier,
                text = "FC Slavia Praha Slaviass",
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp),
                maxLines = 1
            )
        }

    }

}

@Composable
fun CustomLayout(
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val imageConstraints = Constraints.fixed(
            80.dp.toPx().toInt(),
            80.dp.toPx().toInt()
        )

        val width = constraints.maxWidth - ((imageConstraints.maxWidth * 2) + 250)

        val scoreConstraints = Constraints.fixed(
            width,
            30.sp.toPx().toInt()
        )

        val score20Constraints = Constraints.fixed(
            width,
            20.sp.toPx().toInt()
        )

        val nameConstraint = Constraints.fixed(
            (constraints.maxWidth / 2) - 60,
            30.sp.toPx().toInt()
        )



        val imagePlacable = measurables[0].measure(imageConstraints)
        val imagePlacable2 = measurables[1].measure(imageConstraints)
        val scorePlacalbe = measurables[2].measure(scoreConstraints)
        val locationPlacalbe = measurables[3].measure(score20Constraints)
        val datePlacable = measurables[4].measure(score20Constraints)
        val name1 = measurables[5].measure(nameConstraint)
        val name2 = measurables[6].measure(nameConstraint)

        layout(constraints.maxWidth, constraints.maxHeight) {

            val halfHeight = imagePlacable.height / 2
            val halfScreen = ((constraints.maxHeight / 2) - halfHeight)

            imagePlacable.place(x = 10 + (nameConstraint.maxWidth / 2) - imageConstraints.maxWidth / 2 , y = halfScreen)
            imagePlacable2.place((constraints.maxWidth - 10) - (nameConstraint.maxWidth / 2) - imageConstraints.maxWidth / 2,y = halfScreen)
            scorePlacalbe.place(10 + (nameConstraint.maxWidth / 2) + (imageConstraints.maxWidth / 2) ,
                (constraints.maxHeight / 2) - (scoreConstraints.maxHeight / 2))
            locationPlacalbe.place(10 + (nameConstraint.maxWidth / 2) + (imageConstraints.maxWidth / 2) ,
                (constraints.maxHeight / 2) - (scoreConstraints.maxHeight / 2) + 30.sp.toPx().toInt())
            datePlacable.place( 10 + (nameConstraint.maxWidth / 2) + (imageConstraints.maxWidth / 2) ,
                (constraints.maxHeight / 2) - (scoreConstraints.maxHeight / 2) - 20.sp.toPx().toInt())

            name1.place(16,halfScreen + 80.dp.toPx().toInt() + 10)
            name2.place(constraints.maxWidth - 16 - name2.width ,halfScreen + 80.dp.toPx().toInt() + 10)

        }
    }
}