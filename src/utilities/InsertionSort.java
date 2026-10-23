package utilities;

import java.util.Comparator;

import products.Product;

/**
 * <p>
 * Insertion sort that arranges an array of {@link Product} objects from
 * largest to smallest (descending order).
 * </p>
 * <p>
 * The sort never sorts ascending and reverses; it uses the product's
 * <code>compareTo()</code> method or a {@link Comparator} directly and places
 * an element earlier in the array when it compares as greater. The sort is
 * stable and contains no timing or printing code.
 * </p>
 */
public class InsertionSort
{
	/** Not meant to be instantiated; all methods are static. */
	private InsertionSort()
	{
	}

	/**
	 * Sorts the products in descending order using their
	 * <code>compareTo()</code> method (the calculated difference).
	 *
	 * @param array The array of products to sort in place.
	 */
	public static void sort( Product[] array )
	{
		for ( int i = 1; i < array.length; i++ )
		{
			Product key = array[i];
			int j = i - 1;

			// shift smaller products one place to the right
			while ( j >= 0 && array[j].compareTo( key ) < 0 )
			{
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = key;
		}
	}

	/**
	 * Sorts the products in descending order using the given comparator.
	 *
	 * @param array      The array of products to sort in place.
	 * @param comparator Compares two products; a positive result means the first
	 *                   product is greater than the second.
	 */
	public static void sort( Product[] array, Comparator<Product> comparator )
	{
		for ( int i = 1; i < array.length; i++ )
		{
			Product key = array[i];
			int j = i - 1;

			// shift smaller products one place to the right
			while ( j >= 0 && comparator.compare( array[j], key ) < 0 )
			{
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = key;
		}
	}
}
