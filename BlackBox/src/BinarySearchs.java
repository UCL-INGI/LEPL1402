package src;

class BinarySearchCorrect extends BinarySearchFlavour {

    public int binarySearch(int [] arr, int low, int high, int elem) {
        if (!checkPre(arr, low, high))
            return -2;

        while (high >= low) {
            int mid = (low + high)/2;
            if (arr[mid] == elem)
                return mid;
            if (arr[mid] > elem) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    public boolean correctness() {
        return true;
    }

    public String feedbackMessage() {
        return "Correct implementation, iterative version";
    }
}

class BinarySearchRecursive extends BinarySearchFlavour {

    public int binarySearch(int [] arr, int  low, int high, int elem) {
        if (!checkPre(arr, low, high))
            return -2;
        return recursive(arr, low, high, elem);
    }

    private int recursive(int [] arr, int low, int high, int elem) {
        if (low > high)
            return -1;
        int mid = (low + high)/2;
        if (arr[mid] == elem)
            return mid;
        if (arr[mid] > elem)
            return recursive(arr, low, mid-1, elem);
        return recursive(arr, mid+1, high, elem);
    }

    public boolean correctness() {
        return true;
    }

    public String feedbackMessage() {
        return "Correct implementation, recursive version";
    }
}


class BinarySearchErr2 extends BinarySearchFlavour {

    public boolean correctness() {
        return false;
    }

    public String feedbackMessage() {
        return "Do not return the index on match";
    }

    public int binarySearch(int [] arr, int low, int high, int elem) {
        if (!checkPre(arr, low, high))
            return -2;

        while (high >= low) {
            int mid = (low + high)/2;
            if (arr[mid] == elem)
                return elem; // should return mid
            if (arr[mid] > elem)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -1;
    }
}

class BinarySearchErr3 extends BinarySearchFlavour {

    public boolean correctness() {
        return false;
    }

    public String feedbackMessage() {
        return "Bad update of the high index";
    }

    @Override
    public int binarySearch(int [] arr, int low, int high, int elem) {
        if (!checkPre(arr, low, high))
            return -2;

        while (high >= low) {
            int mid = (low + high)/2;
            if (arr[mid] == elem)
                return mid;
            if (arr[mid] > elem)
                high = mid + 1; // should be mid - 1
            else
                low = mid + 1;
        }
        return -1;
    }
}

class BinarySearchErr4 extends BinarySearchFlavour {

    public boolean correctness() {
        return false;
    }

    public String feedbackMessage() {
        return "Bad update of the low index";
    }

    @Override
    public int binarySearch(int [] arr, int low, int high, int elem) {
        if (!checkPre(arr, low, high))
            return -2;

        while (high >= low) {
            int mid = (low + high)/2;
            if (arr[mid] == elem)
                return mid;
            if (arr[mid] > elem)
                high = mid - 1;
            else
                low = mid - 1; // should be mid + 1
        }
        return -1;
    }
}

class BinarySearchErr5 extends BinarySearchFlavour {

    public boolean correctness() {
        return false;
    }

    public String feedbackMessage() {
        return "Bad return when not found";
    }

    public int binarySearch(int [] arr, int low, int high, int elem) {
        if (!checkPre(arr, low, high))
            return -2;

        while (high >= low) {
            int mid = (low + high)/2;
            if (arr[mid] == elem)
                return mid;
            if (arr[mid] > elem)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -2; // should return -1 when not found
    }
}

class BinarySearchErr8 extends BinarySearchFlavour {

    public boolean correctness() {
        return false;
    }
    public String feedbackMessage() {
        return "Bad return when low > high";
    }

    public int binarySearch(int [] arr, int low, int high, int elem) {
        if (low > high)
            return -1; // should be -2

        if (!checkPre(arr, low, high))
            return -2;

        while (high >= low) {
            int mid = (low + high)/2;
            if (arr[mid] == elem)
                return mid;
            if (arr[mid] > elem)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -1;
    }
}

class BinarySearchErr9 extends BinarySearchFlavour {

    public boolean correctness() {
        return false;
    }
    public String feedbackMessage() {
        return "Bad return when low < 0";
    }

    public int binarySearch(int [] arr, int low, int high, int elem) {
        if (low < 0)
            return -1; // should be -2

        if (!checkPre(arr, low, high))
            return -2;

        while (high >= low) {
            int mid = (low + high)/2;
            if (arr[mid] == elem)
                return mid;
            if (arr[mid] > elem)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -1;
    }
}

class BinarySearchErr7 extends BinarySearchFlavour {

    public boolean correctness() {
        return false;
    }
    public String feedbackMessage() {
        return "Bad return when high > array.length - 1";
    }

    public int binarySearch(int [] arr, int low, int high, int elem) {
        if (high > arr.length - 1)
            return -1; // should be -2
        if (!checkPre(arr, low, high))
            return -2;

        while (high >= low) {
            int mid = (low + high)/2;
            if (arr[mid] == elem)
                return mid;
            if (arr[mid] > elem)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -1;
    }
}

class BinarySearchErr10 extends BinarySearchFlavour {

    public boolean correctness() {
        return false;
    }
    public String feedbackMessage() {
        return "Bad return when array is null";
    }

    public int binarySearch(int [] arr, int low, int high, int elem) {
        if (arr == null)
            return -1; // should be -2
        if (!checkPre(arr, low, high))
            return -2;

        while (high >= low) {
            int mid = (low + high)/2;
            if (arr[mid] == elem)
                return mid;
            if (arr[mid] > elem)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -1;
    }
}

class BinarySearchErr11 extends BinarySearchFlavour {

    public boolean correctness() {
        return false;
    }
    public String feedbackMessage() {
        return "Bad return when array is not sorted in increasing order";
    }

    public int binarySearch(int [] arr, int low, int high, int elem) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > arr[i+1])
                    return -1; // should be -2
            }
        }
        if (!checkPre(arr, low, high))
            return -2;

        while (high >= low) {
            int mid = (low + high)/2;
            if (arr[mid] == elem)
                return mid;
            if (arr[mid] > elem)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -1;
    }
}
