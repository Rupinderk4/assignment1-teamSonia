package utilities;

import java.util.Comparator;
import products.Product;

/**
 * Sorts Product arrays in descending order using QuickSort.
 * Selects the pivot using the median-of-three approach.
 */
public class QuickSort
{
    /**
     * Prevents creation of this utility class.
     */
    private QuickSort()
    {
    }

    /**
     * Sorts products by calculated difference in descending order.
     *
     * @param array the products to sort
     * @throws IllegalArgumentException if the array is null
     */
    public static void sort(Product[] array)
    {
        sort(array, null);
    }

    /**
     * Sorts products in descending order using a comparator.
     * A null comparator uses Product.compareTo().
     *
     * @param array the products to sort
     * @param comparator the price comparator, or null for natural order
     * @throws IllegalArgumentException if the array is null
     */
    public static void sort(
            Product[] array, Comparator<Product> comparator)
    {
        if (array == null)
        {
            throw new IllegalArgumentException(
                    "The product array cannot be null.");
        }

        quickSort(array, 0, array.length - 1, comparator);
    }

    /**
     * Partitions a section and recursively sorts both sides.
     *
     * @param array the product array
     * @param low the first index
     * @param high the last index
     * @param comparator the comparator, or null for natural order
     */
    private static void quickSort(
            Product[] array, int low, int high,
            Comparator<Product> comparator)
    {
        // Zero or one product is already sorted.
        if (low >= high)
        {
            return;
        }

        int pivotIndex = partition(array, low, high, comparator);

        quickSort(array, low, pivotIndex - 1, comparator);
        quickSort(array, pivotIndex + 1, high, comparator);
    }

    /**
     * Selects a median-of-three pivot and places it in its final position.
     * Values on the left are greater than or equal to the pivot.
     * Values on the right are less than or equal to the pivot.
     *
     * @param array the product array
     * @param low the first index
     * @param high the last index
     * @param comparator the comparator, or null for natural order
     * @return the final pivot index
     */
    private static int partition(
            Product[] array, int low, int high,
            Comparator<Product> comparator)
    {
        int middle = low + (high - low) / 2;

        // Arrange the first, middle and last products descending.
        if (compare(array[low], array[middle], comparator) < 0)
        {
            swap(array, low, middle);
        }

        if (compare(array[low], array[high], comparator) < 0)
        {
            swap(array, low, high);
        }

        if (compare(array[middle], array[high], comparator) < 0)
        {
            swap(array, middle, high);
        }

        // Move the median product to the end temporarily.
        swap(array, middle, high);
        Product pivot = array[high];

        int left = low;
        int right = high - 1;

        while (left <= right)
        {
            // Products larger than the pivot belong on the left.
            while (left <= right
                    && compare(array[left], pivot, comparator) > 0)
            {
                left++;
            }

            // Products smaller than the pivot belong on the right.
            while (left <= right
                    && compare(array[right], pivot, comparator) < 0)
            {
                right--;
            }

            if (left <= right)
            {
                swap(array, left, right);
                left++;
                right--;
            }
        }

        // Put the pivot between the two partitions.
        swap(array, left, high);

        return left;
    }

    /**
     * Compares two products using the selected comparison method.
     *
     * @param first the first product
     * @param second the second product
     * @param comparator the comparator, or null for natural order
     * @return a negative value, zero, or a positive value
     */
    private static int compare(
            Product first, Product second,
            Comparator<Product> comparator)
    {
        if (comparator == null)
        {
            return first.compareTo(second);
        }

        return comparator.compare(first, second);
    }

    /**
     * Exchanges two products in the array.
     *
     * @param array the product array
     * @param first the first index
     * @param second the second index
     */
    private static void swap(
            Product[] array, int first, int second)
    {
        Product temporary = array[first];
        array[first] = array[second];
        array[second] = temporary;
    }
}
