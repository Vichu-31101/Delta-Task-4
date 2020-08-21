package com.example.delta_task_4

class Files {
    var name: String = ""
    var isExpanded = false
    var level = 0
    var arrow = false
    var subFiles = mutableListOf<Files>()
}