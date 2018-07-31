package com.soft_industry.findgift

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.soft_industry.findgift.data.dto.gifts.GiftTargetParent
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.soft_industry.findgift", appContext.packageName)

        val presents = loadPresents()
        mapCategoryToPresents(presents, appContext)

    }

    private fun mapCategoryToPresents(presents: MutableMap<String, Int>, appContext: Context) {
        val stream = InputStreamReader(appContext.getAssets()
                .open("categories_sorted_formatted.csv"))

        val reader = BufferedReader(stream)
        var line: String? = reader.readLine()
//        val labels = getTargetLabels()
        val parents = getTargetParents()
        var i=0;
        while (line  != null) {
            line?.apply {
                val data = split(";")
                val targetId = i++
                val category = data[0].toLowerCase().trim()
//                val label = labels.get(category)
                val parent = parents.get(category)
                println("insert into gift_targets values ('$targetId', 'target_$category', '$category', '$parent');")
                val catPesents = data[1].replace("\"", "").split(",")
                for (present in catPesents) {
                    val giftId = presents.get(present)

                    giftId?.let {
                        println("insert into targets_gift values ($giftId, $targetId);")
                    }

                }

            }
            line = reader.readLine()
        }
    }

    private fun getTargetParents(): Map<String, Int> {
        return mapOf(
                Pair("forwomen", GiftTargetParent.Editors.id),
                Pair("formen", GiftTargetParent.Editors.id),
                Pair("forchildren", GiftTargetParent.Editors.id),
                Pair("hobby", GiftTargetParent.Editors.id),
                Pair("celebration", GiftTargetParent.Editors.id),
                Pair("profession", GiftTargetParent.Editors.id),
                Pair("housewife", GiftTargetParent.Thematic.id),
                Pair("photo", GiftTargetParent.Thematic.id),
                Pair("music", GiftTargetParent.Thematic.id),
                Pair("work", GiftTargetParent.Thematic.id),
                Pair("creative", GiftTargetParent.Thematic.id),
                Pair("cooking", GiftTargetParent.Thematic.id),
                Pair("driver", GiftTargetParent.Thematic.id),
                Pair("fisher", GiftTargetParent.Thematic.id),
                Pair("hunter", GiftTargetParent.Thematic.id),
                Pair("itperson", GiftTargetParent.Thematic.id),
                Pair("sport", GiftTargetParent.Thematic.id),
                Pair("coffe", GiftTargetParent.Thematic.id),
                Pair("wine", GiftTargetParent.Thematic.id),
                Pair("book", GiftTargetParent.Thematic.id),
                Pair("traveling", GiftTargetParent.Thematic.id),
                Pair("game", GiftTargetParent.Thematic.id),
                Pair("film", GiftTargetParent.Thematic.id),
                Pair("mother", GiftTargetParent.ForWomen.id),
                Pair("mother_in_low", GiftTargetParent.ForWomen.id),
                Pair("grandmother", GiftTargetParent.ForWomen.id),
                Pair("wife", GiftTargetParent.ForWomen.id),
                Pair("girlfriend", GiftTargetParent.ForWomen.id),
                Pair("sister", GiftTargetParent.ForWomen.id),
                Pair("daughterinlow", GiftTargetParent.ForWomen.id),
                Pair("daughter", GiftTargetParent.ForWomen.id),
                Pair("aunt", GiftTargetParent.ForWomen.id),
                Pair("friend", GiftTargetParent.ForWomen.id),
                Pair("boyfriend", GiftTargetParent.ForMen.id),
                Pair("husband", GiftTargetParent.ForMen.id),
                Pair("brother", GiftTargetParent.ForMen.id),
                Pair("soninlow", GiftTargetParent.ForMen.id),
                Pair("grandfather", GiftTargetParent.ForMen.id),
                Pair("father", GiftTargetParent.ForMen.id),
                Pair("son", GiftTargetParent.ForMen.id),
                Pair("uncle", GiftTargetParent.ForMen.id),
                Pair("pal", GiftTargetParent.ForMen.id)

        )
    }


    private fun getTargetLabels() = mapOf(
            Pair("forwomen", "For women"),
            Pair("formen", "For men"),
            Pair("forchildren", "For children"),
            Pair("hobby", "Hobby and lifestyle"),
            Pair("celebration", "Occasions"),
            Pair("profession", "Profession"),
            Pair("housewife", "Housewife"),
            Pair("photo", "Photographer"),
            Pair("music", "Music"),
            Pair("work", "Workaholic"),
            Pair("creative", "Creative"),
            Pair("cooking", "Cooking lover"),
            Pair("driver", "Driver"),
            Pair("fisher", "Fisher"),
            Pair("hunter", "Hunter"),
            Pair("itperson", "IT person"),
            Pair("sport", "Sportsman"),
            Pair("coffe", "Coffee nerd"),
            Pair("wine", "Wine connoisseur"),
            Pair("book", "Bibliophile"),
            Pair("traveling", "Tourist"),
            Pair("game", "Gamer"),
            Pair("film", "Cinephile"),
            Pair("mother", "Mother"),
            Pair("mother_in_low", "Mother-in-low"),
            Pair("grandmother", "Grand-mother"),
            Pair("wife", "Wife"),
            Pair("girlfriend", "Girlfriend"),
            Pair("sister", "Sister"),
            Pair("daughterinlow", "Daughter-in-low"),
            Pair("daughter", "Daughter"),
            Pair("aunt", "Aunt"),
            Pair("friend", "Friend"),
            Pair("boyfriend", "Boyfriend"),
            Pair("husband", "Husband"),
            Pair("brother", "Brother"),
            Pair("soninlow", "Son-in-low"),
            Pair("grandfather", "Grandfather"),
            Pair("father", "Father"),
            Pair("son", "Son"),
            Pair("uncle", "Uncle"),
            Pair("pal" ,"Pal")
    )

    private fun loadPresents(): MutableMap<String, Int> {
        val presents = arrayOf("actor_card", "alcohol", "cookbook", "art_card", "autoregistrator", "backgammon", "bag_m", "bag_w", "baloon_card", "belt", "bike", "billiards_card", "book", "buggy_driving", "camera", "ceramic_teapot", "cigars", "clothes_m", "concert_tikets", "cookies_form", "cosmetics_m", "diary", "dishes2", "dishes", "djlessons_card", "drivinglessons_card", "drums_card", "electric_shaver", "ereader", "excursion_card", "extreme_driving_card", "fishing_card", "fishing", "flashmemory", "food_processor", "foot_warmer", "game_for_couple", "gamesclub_card", "glasses", "grilkit", "guitarlessons_card", "gym_m", "gym_w", "headphones", "health_m", "health_w", "itrest_card", "jeverly_box", "juicer", "karting_card", "keyholder", "laptop", "magicball", "massage_card", "massager", "mirror", "model", "pan", "parfume_m_1", "parfume_m", "phone_m", "phone_w", "photo_card", "poncho", "pottery_card", "ps", "purse", "quest_card", "quilling_card", "razor", "riding_card", "ridingtour_card", "romantic_deinner", "seat_covers", "self_defense_card", "shavingkit", "Shop_card_m", "shopping_card", "skateboard", "skewerkit", "sleepwear_m", "smartwatch", "smoking_pipe", "sneakers", "soccer_ticket", "spa_card_1", "spa_card", "sport_stuff", "sushi_card", "taepot", "tennis_card", "theatre_tickets", "thermocup", "tonometr", "tools", "travel_case", "travel_tour_card", "traveltour_tickets", "umbrella_m", "umbrella_w", "vocal_card", "watch_m", "watch_w", "weekend_card", "xbox", "yoga_card")
                .map { it.toLowerCase() }
        val presentToId = mutableMapOf<String, Int>()
        for (i in 0 until presents.size) {
            println("insert  into gifts (id, name, icon, caption) values ('$i', '${presents[i]}', 'ic_gift_${presents[i]}', 'Bacon ipsum dolor amet short ribs burgdoggen pork chop shoulder, pancetta picanha fatback hamburger');")
            presentToId.put(presents[i], i)
        }
        return presentToId
    }
}
