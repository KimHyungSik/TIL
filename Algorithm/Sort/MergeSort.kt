
fun main() {
    val array = mutableListOf<Int>(2,3,1,6,8,4,5,9)
    mergeSort(array, 0, array.size - 1)
    print(array)
}

fun mergeSort(array: MutableList<Int>, start: Int, end: Int) {
    // 가장 작은 단위 까지 분활
    if (start >= end) return
    
    // 재귀로 분활
    val mid = (start + end) / 2
    mergeSort(array, start, mid)
    mergeSort(array, mid + 1, end)

    // 분활한 array 합칙
    merge(array, start, mid, end)
}

fun merge(array: MutableList<Int>, start: Int, mid: Int, end: Int){

    val newList = mutableListOf<Int>()
    var indexA = start
    var indexB = mid + 1

    // 두개의 리스트를 돌면서 병합
    while ( indexA <= mid && indexB <= end){
        if(array[indexA] <= array[indexB]){
            newList.add(array[indexA])
            indexA++
        }else{
            newList.add(array[indexB])
            indexB++
        }
    }
    
    // 남은 리스트 추가
    if (indexA > mid) {
        for (i in indexB..end) {
            newList.add(array[i])
        }
    }

    if (indexB > end) {
        for (i in indexA..mid) {
            newList.add(array[i])
        }
    }

    // 합병
    for (e in newList.indices) {
        array[start + e] = newList[e]
    }
}
