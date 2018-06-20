package java.androidx.core.content

import android.content.Context

/**
 * Created by user on 4/11/18.
 */


/**
 * Retrieve drawable's resource identifier by its name
 */
fun Context.findDrawalbeIdByName(name: String): Int {
    val type = "drawable"
    return getResourceId(name, type)
}
/**
 * Retrieve string's resource identifier by its name
 */
fun Context.findStringIdByName(name: String): Int {
    val type = "string"
    return getResourceId(name, type)

}

/**
 * Retrieve resource identifier by its name and type
 * @param name resource name
 * @param type resource type
 */
fun Context.getResourceId(name: String, type: String): Int {
    return resources.getIdentifier(name, type, packageName)
}