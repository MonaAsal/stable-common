package com.moddakir.moddakir.view.bases.listeners

    interface RecyclerItemListener<T> {
        fun onItemSelected(item: T)
    }

    interface RecyclerItemMultiSelectionListener<T> {
        fun onItemMultiSelected(item: T)
        fun onItemDeSelected(item: T)
    }
