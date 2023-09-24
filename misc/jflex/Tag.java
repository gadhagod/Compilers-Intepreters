import java.util.Stack;

/**
 * Represents an HTML tag
 * 
 * @author  Aarav Borthakur
 * @version 9/12/23
 */
public class Tag
{
    private static Stack<String> tags = new Stack<String>();
    private String tagName;

    /**
     * Tests whether a character is a letter or dash
     * @param target    The char to test
     * @return          Whether a character is a letter or dash
     */
    private static boolean isLetter(char target)
    {
        return Character.isLetter(target) || target == '_';
    }

    /**
     * Constructs a Tag given its closer or opener XML tag
     * @param tagContets    The closer or opener XML tag
     * @param opener        Whether the tag is an opener
     */
    public Tag(String tagContents, boolean opener)
    {
        int i = 0;
        while (!isLetter(tagContents.charAt(i)))
        {
            i++;
        }
        tagName = "";
        while (isLetter(tagContents.charAt(i)))
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

    /**
     * Gets the name of the Tag, for example if the tag XML
     * is "<name>", getTagName() returns "name"
     * @return  The tag anme
     */
    public String getTagName()
    {
        return tagName;
    }

    /**
     * Checks that all tags that have been opened 
     * have also been closed
     * @return   Whether all tags that have been opened
     *           have also been closed
     */
    public static void assertTagsClosed()
    {
        if (!tags.isEmpty())
        {
            System.out.println("Tags unmatched: " + tags);
            //throw new ScanErrorException("Tags unmatched: " + tags);
        }
    }
}
