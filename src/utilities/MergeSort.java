package utilities;

import java.util.Comparator;

import products.Product;

/**
 * <p>
 * Merge sort that arranges an array of {@link Product} objects from largest to
 * smallest (descending order).
 * </p>
 * <p>
 * The sort never sorts ascending and reverses; it uses the product's
 * <code>compareTo()</code> method or a {@link Comparator} directly and, while
 * merging, takes the greater product first. The sort is stable and contains no
 * timing or printing code.
 * </p>
 */
public class MergeSort
{
	/** Not meant to be instantiated; all methods are static. */
	private MergeSort()
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
		if ( array.length < 2 )
		{
			return;
		}
		Product[] helper = new Product[array.length];
		mergeSort( array, helper, 0, array.length - 1, null );
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
		if ( array.length < 2 )
		{
			return;
		}
		Product[] helper = new Product[array.length];
		mergeSort( array, helper, 0, array.length - 1, comparator );
	}

	/**
	 * Recursively splits the range in half, sorts each half and merges them.
	 *
	 * @param array      The array being sorted.
	 * @param helper     A scratch array of the same length, used while merging.
	 * @param low        The first index of the range (inclusive).
	 * @param high       The last index of the range (inclusive).
	 * @param comparator Compares two products, or null to use compareTo().
	 */
	private static void mergeSort( Product[] array, Product[] helper, int low, int high, Comparator<Product> comparator )
	{
		if ( low >= high )
		{
			return;
		}
		int mid = low + ( high - low ) / 2;
		mergeSort( array, helper, low, mid, comparator );
		mergeSort( array, helper, mid + 1, high, comparator );
		merge( array, helper, low, mid, high, comparator );
	}

	/**
	 * Merges two sorted neighbouring ranges, [low..mid] and [mid+1..high], into
	 * one sorted range. The greater product is taken first (descending); when
	 * products are equal the left one is taken so the sort stays stable.
	 *
	 * @param array      The array being sorted.
	 * @param helper     A scratch array of the same length.
	 * @param low        The first index of the left range.
	 * @param mid        The last index of the left range.
	 * @param high       The last index of the right range.
	 * @param comparator Compares two products, or null to use compareTo().
	 */
	private static void merge( Product[] array, Product[] helper, int low, int mid, int high, Comparator<Product> comparator )
	{
		System.arraycopy( array, low, helper, low, high - low + 1 );

		int left = low;
		int right = mid + 1;
		int current = low;

		while ( left <= mid && right <= high )
		{
			int result;
			if ( comparator == null )
			{
				result = helper[left].compareTo( helper[right] );
			}
			else
			{
				result = comparator.compare( helper[left], helper[right] );
			}

			if ( result >= 0 )
			{
				array[current++] = helper[left++];
			}
			else
			{
				array[current++] = helper[right++];
			}
		}

		// any leftovers on the left side still need copying; the right side is already in place
		while ( left <= mid )
		{
			array[current++] = helper[left++];
		}
	}
}
