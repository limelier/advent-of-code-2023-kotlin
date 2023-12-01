internal class InputReader(
    private val resourceFilePath: String,
) {
    fun lines(): List<String> = this.javaClass.getResource(resourceFilePath)
        ?.readText()?.trim()?.lines()
        ?: throw IllegalArgumentException("Non-existent resource path: $resourceFilePath")
}