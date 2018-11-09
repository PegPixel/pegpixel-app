package org.md.pegpixel.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.md.pegpixel.BoardView
import org.md.pegpixel.R


@RunWith(AndroidJUnit4::class)
@LargeTest
class PegboardUITests {

    @get:Rule
    var activityRule: ActivityTestRule<BoardView> = ActivityTestRule(BoardView::class.java)

    @Test
    fun saveAndLoadPegboard() {
        val pegboardViewObject = PegboardViewObject()
        pegboardViewObject.clickFirstPeg()
        pegboardViewObject.setBoardName()
        pegboardViewObject.saveCurrentBoard()

        pegboardViewObject.clickFirstPeg()
        pegboardViewObject.clickSecondPeg()

        pegboardViewObject.loadBoard()

        pegboardViewObject.checkThatFirstPeg(matches(isChecked()))
        pegboardViewObject.checkThatSecondPeg(matches(isNotChecked()))
    }
}

class PegboardViewObject{

    private val firstPeg = onView(withId(0))
    private val secondPeg = onView(withId(1))
    private val boardNameField = onView(withId(R.id.boardName))
    private val saveButton = onView(withId(R.id.saveButton))
    private val loadButton = onView(withId(R.id.loadButton))

    fun clickFirstPeg() {
        firstPeg.perform(click())
    }
    fun clickSecondPeg() {
        secondPeg.perform(click())
    }
    fun setBoardName() {
        boardNameField.perform(replaceText("Saved Board Name"), closeSoftKeyboard())
    }
    fun saveCurrentBoard(){
        saveButton.perform(click())
    }
    fun loadBoard() {
        loadButton.perform(click())
    }

    fun checkThatFirstPeg(matches: ViewAssertion?) {
        firstPeg.check(matches)
    }
    fun checkThatSecondPeg(matches: ViewAssertion?) {
        secondPeg.check(matches)
    }
}
