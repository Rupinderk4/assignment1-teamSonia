package utilities;

import java.util.Comparator;

import products.Product;

/**
 * <p>
 * Heapsort, the custom sorting algorithm (option {@code z}) for the
 * Complexity and Sorting assignment. It sorts an array of {@link Product}
 * objects from largest to smallest (descending order).
 * </p>
 * <p>
 * The array is treated as a min-heap: a binary tree where every parent is
 * less than or equal to its children, so the smallest product is always at
 * index 0. For the element at index {@code i}, its children are at
 * {@code 2i + 1} and {@code 2i + 2}. The smallest product is repeatedly
 * swapped to the end of the unsorted part, so the array fills from the back
 * and ends up in descending order. It is never sorted ascending and then
 * reversed.
 * </p>
 * <p>
 * Time complexity: O(n log n). Space complexity: O(1).
 * </p>
 */
public final class HeapSort
{
	/**
	 * Private constructor, because this class only has static methods.
	 */
	private HeapSort()
	{
	}

	/**
	 * Sorts the products from largest to smallest using
	 * {@link Product#compareTo(Product)}, which {@code Product} implements
	 * from the {@link Comparable} interface. This is used for the {@code d}
	 * (calculated difference) compare type.
	 *
	 * @param array the products to sort
	 * @throws IllegalArgumentException if the array is null
	 */
	public static void heapSort( Product[] array )
	{
		if( array == null )
		{
			throw new IllegalArgumentException( "Heap sort error: the array to sort cannot be null." );
		}

		// compare() uses compareTo().
		sortDescending( array, null );
	}

	/**
	 * Sorts the products from largest to smallest using the given
	 * {@link Comparator}. This is used for the {@code o} (original price) and
	 * {@code u} (updated price) compare types.
	 *
	 * @param array      the products to sort
	 * @param comparator the comparator that compares two products
	 * @throws IllegalArgumentException if the array or the comparator is null
	 */
	public static void heapSort( Product[] array, Comparator<Product> comparator )
	{
		if( array == null )
		{
			throw new IllegalArgumentException( "Heap sort error: the array to sort cannot be null." );
		}
		if( comparator == null )
		{
			throw new IllegalArgumentException( "Heap sort error: the comparator cannot be null." );
		}

		sortDescending( array, comparator );
	}

	/**
	 * Runs the heapsort for both {@code heapSort()} methods. Phase 1 builds a
	 * min-heap. Phase 2 repeatedly swaps the smallest product (index 0) to
	 * the end of the heap, shrinks the heap by one, and repairs it.
	 *
	 * @param array      the products to sort
	 * @param comparator the comparator to use, or {@code null} to use
	 *                   {@code compareTo()}
	 */
	private static void sortDescending( Product[] array, Comparator<Product> comparator )
	{
		int n = array.length;

		// Phase 1: build the min-heap. Leaves are already valid heaps, so
		// start at the last parent (n / 2 - 1) and work back to the root.
		for( int i = n / 2 - 1; i >= 0; i-- )
		{
			siftDown( array, i, n, comparator );
		}

		// Phase 2: move the smallest product (the root) to the end of the
		// heap, where it is in its final place, then shrink the heap and
		// repair it from the root.
		for( int end = n - 1; end > 0; end-- )
		{
			swap( array, 0, end );
			siftDown( array, 0, end, comparator );
		}
	}

	/**
	 * Moves the product at {@code parent} down the heap, swapping it with its
	 * smaller child, until it is not greater than either child. Only the
	 * first {@code heapSize} elements are part of the heap.
	 *
	 * @param array      the array that holds the heap
	 * @param parent     the index of the product to move down
	 * @param heapSize   the number of elements in the heap
	 * @param comparator the comparator to use, or {@code null} to use
	 *                   {@code compareTo()}
	 */
	private static void siftDown( Product[] array, int parent, int heapSize, Comparator<Product> comparator )
	{
		int child = 2 * parent + 1; // the left child

		while( child < heapSize )
		{
			// Use the right child instead if it exists and is smaller.
			if( child + 1 < heapSize && compare( array[child + 1], array[child], comparator ) < 0 )
			{
				child = child + 1;
			}

			// The parent is not greater than its smaller child, so the
			// min-heap rule holds and nothing more needs to move.
			if( compare( array[parent], array[child], comparator ) <= 0 )
			{
				return;
			}

			// Swap the parent with its smaller child and go down one level.
			swap( array, parent, child );
			parent = child;
			child = 2 * parent + 1;
		}
	}

	/**
	 * Compares two products with the comparator, or with
	 * {@code compareTo()} if the comparator is {@code null}.
	 *
	 * @param first      the first product
	 * @param second     the second product
	 * @param comparator the comparator to use, or {@code null} to use
	 *                   {@code compareTo()}
	 * @return a negative number, zero, or a positive number if {@code first}
	 *         is less than, equal to, or greater than {@code second}
	 */
	private static int compare( Product first, Product second, Comparator<Product> comparator )
	{
		if( comparator == null )
		{
			return first.compareTo( second );
		}

		return comparator.compare( first, second );
	}

	/**
	 * Swaps two elements of the array.
	 *
	 * @param array the array that holds the elements
	 * @param i     the index of the first element
	 * @param j     the index of the second element
	 */
	private static void swap( Product[] array, int i, int j )
	{
		Product temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
