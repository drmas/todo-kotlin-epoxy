package co.mshaban.todos


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.todo_text),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("test"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.todo_text), withText("test"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(click())

        val appCompatButton = onView(
            allOf(
                withId(R.id.add_button), withText("Add"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val checkBox = onView(
            allOf(
                withId(R.id.todo_checkbox),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.todos_list),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        checkBox.check(matches(isDisplayed()))

        val editText = onView(
            allOf(
                withId(R.id.todo_text), withText(""),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        editText.check(matches(withText("")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
