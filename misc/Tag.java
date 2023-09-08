import java.util.Stack;

public class Tag
{
    private static Stack<String> tags = new Stack<String>();
    private String tagName;

    public Tag(String tagContents, boolean opener)
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
        if (opener)
        {
            tags.push(tagName);
        }
        else if (!tags.pop().equals(tagName)) 
        {
            System.out.println("Tag unmatched: " + tagName);
            System.exit(1);
            //throw new ScanErrorException("Tag unmatched: " + tagName);
        }
    }

    public String getTagName()
    {
        return tagName;
    }

    public static void assertTagsClosed()
    {
        if (!tags.isEmpty())
        {
            System.out.println("Tags unmatched: " + tags);
            System.exit(1);
            //throw new ScanErrorException("Tags unmatched: " + tags);
        }
    }
}
