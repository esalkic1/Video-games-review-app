package ba.etf.rma23.projekat

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.example.spirala.R
import org.hamcrest.Matchers.allOf
import org.junit.Test

class OwnEspressoTests {

    /**
     * Testiranje klika na details u bottom navigation
     *
     * Nakon povratka na home fragment sa game details fragmenta potrebno je da aplikacija zapatmi
     * prethodni game details, te da se nakon klika na details vrati na stari fragment
     *
     * U testu se prvo otvara home fragment, zatim se klikom na prvi element u recycler view prebacuje na game details fragment,
     * nakon toga klikom na home button u bottom navigation vraca se na homeFragment i vrsi se provjera da li details button
     * vraca na detalje o staroj igri
     */
    @Test
    fun testDetailsButtonNavigation() {
        // Pokretanje aktivnosti
        val scenario = launchActivity<MainActivity>()

        // Provjera da li je recycler view vidljiv
        onView(withId(R.id.game_list))
            .check(matches(isDisplayed()))

        // Klik na prvi element u Recycler view
        onView(withId(R.id.game_list))
            .perform(actionOnItemAtPosition<GameListAdapter.GameViewHolder>(0, click()))

        // Provjera da li je GameDetailsFragment vidljiv
        onView(withId(R.id.cover_imageview))
            .check(matches(isDisplayed()))

        // Klik na home button
        onView(withId(R.id.homeItem))
            .perform(click())

        // Provjera da li je Recycler view vidljiv
        onView(withId(R.id.game_list))
            .check(matches(isDisplayed()))

        // Klik na details button
        onView(withId(R.id.gameDetailsItem))
            .perform(click())

        // Provjera da li je GameDetailsFragment vidljiv
        onView(withId(R.id.cover_imageview))
            .check(matches(isDisplayed()))

        //provjera da li je zaista otvorena prava igra
        onView(withId(R.id.item_title_textview)).check(matches( withText("FIFA 23")))
    }


    /**
     * Testiranje novog rasporeda elemenata u GameDetails fragmentu
     *
     * Provjera da li je raspored elemenata u fragmentu ispravan, te također da li su ispravno popunjeni
     * textview elementi i recycler view elementi
     *
     * Test pokrece home activity, provjerava da li je ucitan ispravan fragment, te klikom na prvi element u recycler view
     * prelazi na game details fragment, nakon toga prvo se provjerava raspored elemenata, da li je sve poravnato i postavljeno
     * gdje treba. Zatim se provjerava da li su detalji o odabranoj igri ispravno popunjeni. Na kraju se provjeravaju posebni itemi
     * u recycler view, prvo user review, a zatim i user impression
     */
    @Test
    fun GameDetailsFragmentTest(){
        // Pokretanje aktivnosti
        val scenario = launchActivity<MainActivity>()

        // Provjera da li je recycler view vidljiv
        onView(withId(R.id.game_list))
            .check(matches(isDisplayed()))

        // Klik na prvi element u Recycler view
        onView(withId(R.id.game_list))
            .perform(actionOnItemAtPosition<GameListAdapter.GameViewHolder>(0, click()))

        onView(withId(R.id.item_title_textview)).check(isCompletelyAbove(withId(R.id.cover_imageview)))
        onView(withId(R.id.cover_imageview)).check(isCompletelyAbove(withId(R.id.platform_textview)))
        onView(withId(R.id.developer_textview)).check(isCompletelyBelow(withId(R.id.platform_textview)))
        onView(withId(R.id.developer_textview)).check(isCompletelyAbove(withId(R.id.description_textview)))
        onView(withId(R.id.review_list)).check(isCompletelyBelow(withId(R.id.description_textview)))
        onView(withId(R.id.platform_textview)).check(isCompletelyLeftOf(withId(R.id.release_date_textview)))
        onView(withId(R.id.esrb_rating_textview)).check(isCompletelyRightOf(withId(R.id.publisher_textview)))
        onView(withId(R.id.esrb_rating_textview)).check(isTopAlignedWith(withId(R.id.publisher_textview)))
        onView(withId(R.id.esrb_rating_textview)).check(isBottomAlignedWith(withId(R.id.publisher_textview)))

        onView(withId(R.id.item_title_textview))
            .check(matches(withText("FIFA 23")))
        onView(withId(R.id.platform_textview))
            .check(matches(withText("PlayStation 5")))
        onView(withId(R.id.description_textview))
            .check(matches(withSubstring("Live out your football dreams")))

        onView(withId(R.id.review_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)).check(matches(
            allOf(
            hasDescendant(withId(R.id.username_textview)),
            hasDescendant(withId(R.id.rating_bar))
        )))

        onView(withId(R.id.review_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1)).check(matches(
            allOf(
                hasDescendant(withId(R.id.username_textview)),
                hasDescendant(withId(R.id.review_textview))
            )))

    }

    /** Testiranje rada aplikacije u landscape orijentaciji
     *
     * Potrebno je da u slučaju okretanja uređaja u landscape mode budu prikazana oba fragmenta uporedno
     * Pri prvom pokretanju prikazuju se detalji o prvoj igri sa liste
     *
     * Test pokrece rad aplikacije u landscape orijentaciji, pokreće home activity, a zatim provjerava da li je home fragment
     * vidljiv. Nakon toga vrši se provjera da li je prikazana ispravna igra, te se klikom na recycler view bira druga igra sa liste.
     * Vrši se provjera da li je ispravno promijenjena igra, da li je ispravan raspored fragmenata te se vrši povratak u portrait mode
     * i zatvara aktivnost.
     */

    @Test
    fun testLandscapeOrientation() {
        // Uzimanje instance UI device
        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Promjena orijentacije uređaja u landscape mode
        uiDevice.setOrientationLeft()

        // Pokretanje home aktivnosti
        val activityScenario = launchActivity<MainActivity>()

        // Provjera da li je vidljiv home fragment
        onView(withId(R.id.home_fragment)).check(matches(isDisplayed()))

        // Provjera da li je u game details fragmentu vidljiv naslov igre
        onView(allOf( withId(R.id.item_title_textview), isDescendantOfA(withId(R.id.linearLayout4))))
            .check(matches(withText("FIFA 23")))

        // Klik na drugi element u recycler view
        onView(withId(R.id.game_list))
            .perform(actionOnItemAtPosition<GameListAdapter.GameViewHolder>(1, click()))

                // Provjera da li je promijenjena igra
        onView(allOf( withId(R.id.item_title_textview), isDescendantOfA(withId(R.id.linearLayout4))))
            .check(matches(withText("NBA 2K23")))

        // Provjera da li je home fragment lijevo od details fragmenta
        onView(withId(R.id.game_list)).check(isCompletelyLeftOf(allOf(withId(R.id.item_title_textview), isDescendantOfA(withId(
            R.id.linearLayout4
        )))))

        // Povratak na portrait mode
        uiDevice.setOrientationNatural()

        // Zatvaranje aktivnosti
        activityScenario.close()
    }


}