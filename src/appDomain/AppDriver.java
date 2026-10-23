package appDomain;

/**
 * <p>
 * This application driver code is designed to be used as a basis for the
 * Complexity and Sorting assignment that will be developed in the CPRG304 
 * class at SAIT. The implementors of this applications will be required
 * to add all the correct functionality.
 * </p>
 */
public class AppDriver
{
	/**
	 *  The main method is the entry point of the application.
	 *  
	 *  @param args The input to control the execution of the application.
	 */
	public static void main( String[] args )
	{
        String fileName = null;
String compareType = null;
String sortType = null;

try
{
    for (String argument : args)
    {
        if (argument.length() < 3 || argument.charAt(0) != '-')
        {
            throw new IllegalArgumentException(
                    "Invalid argument: " + argument);
        }

        char option = Character.toLowerCase(argument.charAt(1));
        String value = argument.substring(2);

        switch (option)
        {
            case 'f':
                if (fileName != null)
                {
                    throw new IllegalArgumentException(
                            "Provide -f only once.");
                }

                if (value.trim().isEmpty())
                {
                    throw new IllegalArgumentException(
                            "Provide a file name after -f.");
                }

                fileName = value;
                break;

            case 't':
                if (compareType != null)
                {
                    throw new IllegalArgumentException(
                            "Provide -t only once.");
                }

                if (!value.equalsIgnoreCase("o")
                        && !value.equalsIgnoreCase("u")
                        && !value.equalsIgnoreCase("d"))
                {
                    throw new IllegalArgumentException(
                            "Comparison must be o, u, or d.");
                }

                compareType =
                        value.toLowerCase(java.util.Locale.ROOT);
                break;

            case 's':
                if (sortType != null)
                {
                    throw new IllegalArgumentException(
                            "Provide -s only once.");
                }

                if (value.length() != 1
                        || "bsimqz".indexOf(
                            Character.toLowerCase(value.charAt(0))) < 0)
                {
                    throw new IllegalArgumentException(
                            "Sort must be b, s, i, m, q, or z.");
                }

                sortType =
                        value.toLowerCase(java.util.Locale.ROOT);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown option: -" + option);
        }
    }

    if (fileName == null || compareType == null || sortType == null)
    {
        throw new IllegalArgumentException(
                "Provide all three options: -f, -t, and -s.");
    }

    // Temporary output to check the arguments.
    System.out.println("File: " + fileName);
    System.out.println("Comparison: " + compareType);
    System.out.println("Sorting algorithm: " + sortType);
}
catch (IllegalArgumentException e)
{
    System.err.println("Error: " + e.getMessage());
    System.err.println(
            "Example: -fres/products1.txt -td -sq");
}
	}

}
