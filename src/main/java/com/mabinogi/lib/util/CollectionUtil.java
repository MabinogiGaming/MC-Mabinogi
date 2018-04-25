package com.mabinogi.lib.util;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtil {
	
	/**
	 * Searches for a value and then cycles to the next value in a collection
	 * @param values The list of valid values
	 * @param currentValue The current value
	 * @return The next value
	 */
    public static <T> T cycleCollection(Collection<T> values, T currentValue)
    {
        Iterator<T> iterator = values.iterator();

        while (iterator.hasNext())
        {
            if (iterator.next().equals(currentValue))
            {
                if (iterator.hasNext())
                {
                    return iterator.next();
                }

                return values.iterator().next();
            }
        }
        return iterator.next();
    }

}
