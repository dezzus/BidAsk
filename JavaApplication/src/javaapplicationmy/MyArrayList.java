/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplicationmy;

/**
 *
 * @author PAN747
 */
import java.util.*;

public class MyArrayList<E> implements List<E> {
    private int size = 0;
    private int capacity = 0;
    private final int CAPACITY = 16;
    private Object[] array;

    public MyArrayList() {
        capacity = CAPACITY;
        array = new Object[capacity];
    }

    public MyArrayList(int capacity) {
        this.capacity = capacity;
        array = new Object[capacity];
    }

    private void increaseCapacity() {
        capacity = capacity + 1000;
        Object[] newArray = new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
            array[i] = null;
        }
        array = newArray;
    }

    private void trimToSizeArray() {
        capacity = size + 1;
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }

    @Override
    public boolean add(Object o) {
        if (size >= capacity) {
            increaseCapacity();
        }
        array[size++] = o;
        return true;
    }

    private void shiftToLeft(int start) {
        size--;
        if (size <= 0) {
            return;
        }
        if (size != start) {
            System.arraycopy(array, start + 1, array, start, size - start);
        }
        array[size] = null;
    }

    @Override
    public boolean remove(Object o) {
        if ((size == 0)) {
            return false;
        }
        int i;
        for (i = 0; i < size; i++) {
            if (array[i] == null && o == null) {
                break;
            }
            if ((array[i] != null) && (array[i].equals(o))) {
                break;
            }
        }
        if (i < size) {
            shiftToLeft(i);
            return true;
        }
        return false;
    }

   @Override
    public boolean addAll(Collection c) {
        if (c == null) {
            return false;
        }
        if (c.isEmpty()) {
            return false;
        }
        for (Object item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        if (c == null) {
            return false;
        }
        if (c.isEmpty() || (index < 0)) {
            return false;
        }
        if (index > size) {
            index = size;
        }
        for (Object item : c) {
            add(index++, item);
        }
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        if ((index < size) && (index >= 0)) {
            return (E) array[index];
        }
        return null;
    }

   @Override
    public Object set(int index, Object element) {
        if ((index < size) && (index >= 0)) {
            Object o = array[index];
            array[index] = element;
            return o;
        }
        return null;
    }

    @Override
    public void add(int index, Object element) {
        if (index < 0) {
            return;
        }
        if (size + 1 >= capacity) {
            increaseCapacity();
        }
        if (index > size) {
            index = size;
        }
        for (int i = size; i >= index; i--) {
            array[i + 1] = array[i];
        }
        array[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        Object o = null;
        if ((index < size) && (index >= 0)) {
            o = get(index);
            shiftToLeft(index);
        }
        return (E) o;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    lastIndex = i;
                }
            }
            return lastIndex;
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(array[i])) {
                    lastIndex = i;
                }
            }
        }
        return lastIndex;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            int temp = fromIndex;
            fromIndex = toIndex;
            toIndex = temp;
        }
        if ((fromIndex < 0) || (toIndex > size)) {
            return null;
        }
        List myArrayList = new MyArrayList(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            myArrayList.add(array[i]);
        }
        return myArrayList;
    }

    @Override
    public boolean retainAll(Collection c) {
        if (c == null) {
            return true;
        }
        if (c.size() == 0) {
            clear();
            return true;
        }
        int i = 0;
        boolean modyfied = false;
        while (i < size) {
            if (c.contains(array[i])) {
                i++;
            } else {
                shiftToLeft(i);
                modyfied = true;
            }
        }
        return modyfied;
    }

    @Override
    public boolean removeAll(Collection c) {
        if (c == null) {
            return false;
        }
        if ((c.size() == 0) || (size == 0)) {
            return false;
        }
        boolean modyfied = false;
        int i = 0;
        while (i < size) {
            if (c.contains(array[i])) {
                shiftToLeft(i);
                modyfied = true;
            } else {
                i++;
            }
        }
        return modyfied;
    }

    @Override
    public boolean containsAll(Collection c) {
        if (c == null) {
            return false;
        }
        if (c.size() == 0) {
            return true;
        }
        for (Object e : c) {
            if (contains(e)) {
                ;
            } else {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

}
