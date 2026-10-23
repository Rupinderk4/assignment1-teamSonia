package appDomain;

import java.util.Comparator;
import java.util.Locale;

import products.Product;
import products.OriginalPriceComparator;
import products.UpdatedPriceComparator;

import utilities.HeapSort;
import utilities.InsertionSort;
import utilities.MergeSort;
import utilities.ProductFileException;
import utilities.ProductFileReader;
import utilities.QuickSort;
import utilities.Sorter;

/**
 * <p>
 * This application driver code is designed to be used as a basis for the
 * Complexity and Sorting assignment that will be developed in the CPRG304
 * class at SAIT. The implementors of this applications will be required
 * to add all the correct functionality.
 * </p>
 *
 * <p>
 * Reads and validates command-line arguments and loads products into a
 * Product array. Runs the selected sorting algorithm in descending order
 * using original price, updated price, or calculated difference.
 * Measures the sorting time and displays the required results.
 * Reports invalid arguments and file-reading errors.
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
            // Read and validate the command-line arguments.
            for (String argument : args)
            {
                if (argument.length() < 3 || argument.charAt(0) != '-')
                {
                    throw new IllegalArgumentException(
                            "Invalid argument: " + argument);
                }

                char option =
                        Character.toLowerCase(argument.charAt(1));
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

                        compareType = value.toLowerCase(Locale.ROOT);
                        break;

                    case 's':
                        if (sortType != null)
                        {
                            throw new IllegalArgumentException(
                                    "Provide -s only once.");
                        }

                        if (value.length() != 1
                                || "bsimqz".indexOf(
                                    Character.toLowerCase(
                                        value.charAt(0))) < 0)
                        {
                            throw new IllegalArgumentException(
                                    "Sort must be b, s, i, m, q, or z.");
                        }

                        sortType = value.toLowerCase(Locale.ROOT);
                        break;

                    default:
                        throw new IllegalArgumentException(
                                "Unknown option: -" + option);
                }
            }

            if (fileName == null || compareType == null
                    || sortType == null)
            {
                throw new IllegalArgumentException(
                        "Provide all three options: -f, -t, and -s.");
            }

            // File reading happens before the sorting timer starts.
            Product[] products =
                    ProductFileReader.readProducts(fileName);

            Comparator<Product> comparator = null;

            if (compareType.equals("o"))
            {
                comparator = new OriginalPriceComparator();
            }
            else if (compareType.equals("u"))
            {
                comparator = new UpdatedPriceComparator();
            }

            String algorithmName;

            switch (sortType)
            {
                case "b":
                    algorithmName = "Bubble Sort";
                    break;
                case "s":
                    algorithmName = "Selection Sort";
                    break;
                case "i":
                    algorithmName = "Insertion Sort";
                    break;
                case "m":
                    algorithmName = "Merge Sort";
                    break;
                case "q":
                    algorithmName = "Quick Sort";
                    break;
                default:
                    algorithmName = "Heap Sort";
                    break;
            }

            long start;
            long stop;

            // The d option uses each sort's compareTo version.
            if (compareType.equals("d"))
            {
                switch (sortType)
                {
                    case "b":
                        start = System.currentTimeMillis();
                        Sorter.bubbleSort(products);
                        stop = System.currentTimeMillis();
                        break;

                    case "s":
                        start = System.currentTimeMillis();
                        Sorter.selectionSort(products);
                        stop = System.currentTimeMillis();
                        break;

                    case "i":
                        start = System.currentTimeMillis();
                        InsertionSort.sort(products);
                        stop = System.currentTimeMillis();
                        break;

                    case "m":
                        start = System.currentTimeMillis();
                        MergeSort.sort(products);
                        stop = System.currentTimeMillis();
                        break;

                    case "q":
                        start = System.currentTimeMillis();
                        QuickSort.sort(products);
                        stop = System.currentTimeMillis();
                        break;

                    default:
                        start = System.currentTimeMillis();
                        HeapSort.heapSort(products);
                        stop = System.currentTimeMillis();
                        break;
                }
            }
            else
            {
                // The o and u options use the selected price comparator.
                switch (sortType)
                {
                    case "b":
                        start = System.currentTimeMillis();
                        Sorter.bubbleSort(products, comparator);
                        stop = System.currentTimeMillis();
                        break;

                    case "s":
                        start = System.currentTimeMillis();
                        Sorter.selectionSort(products, comparator);
                        stop = System.currentTimeMillis();
                        break;

                    case "i":
                        start = System.currentTimeMillis();
                        InsertionSort.sort(products, comparator);
                        stop = System.currentTimeMillis();
                        break;

                    case "m":
                        start = System.currentTimeMillis();
                        MergeSort.sort(products, comparator);
                        stop = System.currentTimeMillis();
                        break;

                    case "q":
                        start = System.currentTimeMillis();
                        QuickSort.sort(products, comparator);
                        stop = System.currentTimeMillis();
                        break;

                    default:
                        start = System.currentTimeMillis();
                        HeapSort.heapSort(products, comparator);
                        stop = System.currentTimeMillis();
                        break;
                }
            }

            // Output happens after the sorting timer stops.
            displayProducts(products, compareType);

            System.out.println(algorithmName + " run time was: "
                    + (stop - start) + " milliseconds");
        }
        catch (IllegalArgumentException e)
        {
            System.err.println("Error: " + e.getMessage());
            System.err.println(
                    "Example: -fres/products1.txt -td -sq");
        }
        catch (ProductFileException e)
        {
            System.err.println("File error: " + e.getMessage());
        }
    }

    /**
     * Displays the sorted products at the required positions.
     *
     * @param products the sorted product array
     * @param compareType the selected comparison: o, u, or d
     */
    private static void displayProducts(
            Product[] products, String compareType)
    {
        int count = products.length;

        if (count == 0)
        {
            System.out.println("No products to display.");
            return;
        }

        if (count <= 10)
        {
            for (int i = 0; i < count; i++)
            {
                int position = i + 1;
                String suffix;

                switch (position)
                {
                    case 1:
                        suffix = "st";
                        break;
                    case 2:
                        suffix = "nd";
                        break;
                    case 3:
                        suffix = "rd";
                        break;
                    default:
                        suffix = "th";
                        break;
                }

                printProduct(position + "-" + suffix + " element:",
                        products[i], compareType);
            }
        }
        else
        {
            int interval;

            if (count <= 1000)
            {
                interval = 100;
            }
            else
            {
                interval = 1000;
            }

            printProduct("First element is:",
                    products[0], compareType);

            for (int position = interval; position < count;
                    position += interval)
            {
                printProduct(position + "-th element:",
                        products[position - 1], compareType);
            }

            printProduct("Last element is:",
                    products[count - 1], compareType);
        }
    }

    /**
     * Displays one product and its selected comparison value.
     *
     * @param label the product's position label
     * @param product the product to display
     * @param compareType the selected comparison: o, u, or d
     */
    private static void printProduct(
            String label, Product product, String compareType)
    {
        String valueName;
        double value;

        switch (compareType)
        {
            case "o":
                valueName = "Original Price:";
                value = product.getOriginalPrice();
                break;

            case "u":
                valueName = "Updated Price:";
                value = product.getUpdatedPrice();
                break;

            default:
                valueName = "Calculated Difference:";
                value = product.calcRealDiff();
                break;
        }

        System.out.printf(Locale.ROOT,
                "%-22s %-22s %s %s%n",
                label,
                product.getClass().getName(),
                valueName,
                value);
    }
}