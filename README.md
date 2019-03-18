# text-table-generator
Allows for text based tables to be generated from tabular data with ease!

For details on the API you can view the [Javadocs](doc/index.html)

For a simple example of creating a table, see the following code:
```java
TextTableGenerator generator = new TextTableGenerator();
generator.addColumn(new TextTableColumn("EMPLOYEE"));
generator.addColumn(new TextTableColumn("JOB TITLE"));
generator.addColumn(new TextTableColumn( "SALARY"));

generator.addRow(Arrays.asList(new String[] { "Jane Doe", "CEO", "$1,200,000"}));
generator.addRow(Arrays.asList(new String[] { "John Doe", "Developer", "$51,232"}));
generator.addRow(Arrays.asList(new String[] { "Joe Sellers", "Sales", "(Base) $20,000"}));

generator.getColumnByName("SALARY").setAlignment(TextFormatter.Alignment.RIGHT);
String table = generator.generate();
System.out.println(table);
```

Generatres the following output:
```
+---------------+-------------+------------------+
|   EMPLOYEE    |  JOB TITLE  |      SALARY      |
+---------------+-------------+------------------+
|  Jane Doe     |  CEO        |      $1,200,000  |
|  John Doe     |  Developer  |         $51,232  |
|  Joe Sellers  |  Sales      |  (Base) $20,000  |
+---------------+-------------+------------------+
```

You can data by row or by column, whichever suits your needs and existing data better.
Data values are padded so that all columns in the table have the same number of rows.
