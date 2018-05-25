# Generacion de documentación para Fermax

## Utilizar cuatro pasos para pasar un documento de layout a estructura simple con ids y texts

### Paso 1 (Find / Replace)

* Ejemplo1 (sólo id):

    ```
    ^(?!.*(android:id)|.*(<\w+)).*$ / empty
    ```

* Ejemplo2 (ids + text tags):

    ```
    ^(?!.*(android:id)|.*(android:text\s*=".+")|.*(<\w+)).*$ / empty
    ```

### Paso 2 (Find / Replace)

```
^$\R / empty
```

### Paso 3 (Find / Replace)
```
(<\w+.*)\R.*android:\w+="(.*)" / $1 $2
```

### Paso 4 (Find / Replace)
```
< / empty
```
