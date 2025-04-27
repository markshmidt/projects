package com.gomoku.structure;

// Mateus Sfeir 101484904

public class HashMapCache<K, V> {
    private final MyHashMap<K, V> boardCache = new MyHashMap<>();

    public boolean contains(K key) {
        return boardCache.containsKey(key);
    }

    public V get(K key) {
        return boardCache.get(key);
    }

    public void put(K key, V value) {
        boardCache.put(key, value);
    }

    public void remove(K key) {
        boardCache.remove(key);
    }

    public void clear() {
        boardCache.clear();
    }

    public int size() {
        return boardCache.size();
    }
}
