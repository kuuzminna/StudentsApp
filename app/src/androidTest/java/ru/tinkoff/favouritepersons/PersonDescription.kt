package ru.tinkoff.favouritepersons

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import ru.tinkoff.favouritepersons.domain.Gender

class PersonDescription(
    val name: String = "Leo",
    val lastname: String = "Lampo",
    val title: String = "Mr",
    val gender: String = Gender.MALE.toString(),
    val location: Address = Address(),
    val email: String = "leo.lampo@example.com",
    val phone:String = "06-278-783",
    val age:Int = 72,
    val date: String = "1951-08-01T10:34:45.721Z",
    val picture: String = "men/13.jpg"
) {
    class Address(
        val number: Int = 2194,
        val name: String = "Pyynikintie",
        val city: String = "Hammarland",
        val state: String = "Satakunta",
        val country: String = "Finland",
    )
    fun toJson() : JsonObject = JsonObject().apply {
        add("gender", JsonPrimitive(gender))
        add ("name", JsonObject().apply {
            add("title", JsonPrimitive(title))
            add("first", JsonPrimitive(name))
            add("last", JsonPrimitive(lastname))
        })
        add("location", JsonObject().apply {
            add("street", JsonObject().apply {
                add("number", JsonPrimitive(location.number))
                add("name", JsonPrimitive(location.name))
            })
            add("city", JsonPrimitive(location.city))
            add("state", JsonPrimitive(location.state))
            add("country", JsonPrimitive(location.country))
            add("postcode", JsonPrimitive(17225))
            add("coordinates", JsonObject().apply {
                add("latitude", JsonPrimitive("-24.2735"))
                add("longitude", JsonPrimitive("-64.5634"))
            })
            add("timezone", JsonObject().apply {
                add("offset", JsonPrimitive("-4:00"))
                add("description", JsonPrimitive("Atlantic Time (Canada), Caracas, La Paz"))
            })
        })
        add("email", JsonPrimitive(email))
        add("login", JsonObject().apply {
            add("uuid", JsonPrimitive("874391a1-88af-4f1f-b459-91f283bdbd5c"))
            add("username", JsonPrimitive("goldenmeercat659"))
            add("password", JsonPrimitive("doggy1"))
            add("salt", JsonPrimitive("IIO07d8o"))
            add("md5", JsonPrimitive("8fd03a7d6879044d3dfa6515e819c8b3"))
            add("sha1", JsonPrimitive("12262b076714ebea5cdcd8e6c5daf1f5fe0145b9"))
            add("sha256", JsonPrimitive("35ae8f6ca3112e739a05343f824e2fbb33e54fd1a0681ff5d2b83590016f035c"))
        })
        add("dob", JsonObject().apply {
            add("date", JsonPrimitive("1951-08-01T10:34:45.721Z"))
            add("age", JsonPrimitive(age))
        })
        add("registered", JsonObject().apply {
            add("date", JsonPrimitive(date))
            add("age", JsonPrimitive(2))
        })
        add("phone", JsonPrimitive(phone))
        add("cell", JsonPrimitive("048-033-31-18"))
        add("id", JsonObject().apply {
            add("name", JsonPrimitive("HETU"))
            add("value", JsonPrimitive("NaNNA421undefined"))
        })
        add("picture", JsonObject().apply {
            val url = "https://randomuser.me/api/portraits/"
            add("large", JsonPrimitive("${url}$picture"))
            add("medium", JsonPrimitive("${url}med/$picture"))
            add("thumbnail", JsonPrimitive("${url}thumb/$picture"))
        })
        add("nat", JsonPrimitive("FI"))
    }
}