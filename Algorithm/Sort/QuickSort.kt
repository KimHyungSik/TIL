fun main() {
    val array = intArrayOf(2, 3, 1, 6, 8, 4, 5, 9)
    quickSort(array, 0, array.size - 1)
    array.forEach {
        print("$it, ")
    }
}

fun quickSort(array: IntArray, left: Int, right: Int){
    val index = sort(array, left, right)
    if(left < index - 1){
        quickSort(array, left, index - 1)
    }
    if(index < right){
        quickSort(array, index, right)
    }
}

fun sort(array: IntArray, start: Int, end: Int): Int {
    var left = start
    var right = end
    val pivot = array[(left + right) / 2]

    while (left <= right){
        while (array[left] < pivot){
            left++
        }
        while (array[right] > pivot){
            right--
        }
        if(left <= right){
            val temp = array[left]
            array[left] = array[right]
            array[right] = temp
            left++
            right--
        }
    }
    return left
}
