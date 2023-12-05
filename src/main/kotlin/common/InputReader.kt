package common

/** Reads a text file from /resources/inputs/[relativePath] */
internal class InputReader(
    private val relativePath: String,
) {
    private val resourceFilePath = "/inputs/$relativePath"
    fun lines(): List<String> = this.javaClass.getResource(resourceFilePath)
        ?.readText()?.trim()?.lines()
        ?: throw IllegalArgumentException("Non-existent resource path: $resourceFilePath")
}