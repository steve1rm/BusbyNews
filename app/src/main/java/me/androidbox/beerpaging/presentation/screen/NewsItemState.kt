package me.androidbox.beerpaging.presentation.screen

data class NewsItemState(
    val shouldShowProgress: Boolean = false,
    val showMoreOrLessTitleText: ShowMoreOrLessTitleText = ShowMoreOrLessTitleText(),
//    val showMoreTitleClicked: Boolean = false,
    val shouldShowMoreTitle: Boolean = true,
    val shouldMoreDescriptionClicked: Boolean = false,
    val shouldShowMoreDescription: Boolean = false,
    val isDarkThemeSelected: Boolean = false
)


/** Work in-progress*/
data class ShowMoreOrLessTitleText(
    var text: String = "Show more",
    var shouldShowMoreTitle: Boolean = false,
    val hasTextOverflow: Boolean = false,
    val lineCount: Int = -1
)

fun ShowMoreOrLessTitleText.textUpdate() {
    this.text = if(this.shouldShowMoreTitle)
        "Show more"
    else
        "Show less"
}

fun ShowMoreOrLessTitleText.shouldMoreText() {
    this.shouldShowMoreTitle = this.hasTextOverflow || lineCount > 1
}