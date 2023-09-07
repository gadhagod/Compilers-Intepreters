public class Tag
{
    private String tagName;

    public Tag(String tagContents)
    {
        int i = 0;
        while (!Character.isLetter(tagContents.charAt(i)))
        {
            i++;
        }
        tagName = "";
        while (Character.isLetter(tagContents.charAt(i)))
        {
            tagName += tagContents.substring(i, i + 1);
            i++;
        }
    }

    public String getTagName()
    {
        return tagName;
    }
}
