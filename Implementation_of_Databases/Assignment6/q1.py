# Spark is not installed system-wide
# that is why findspark pip package is needed
import findspark
findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
	.builder \
	.appName("IDB Assignment 6 Query Runner") \
	.getOrCreate()

# read CSV files into DataFrames
invLineDF = spark.read.csv("csv_files/invoiceline.csv",header=True);
trackDF = spark.read.csv("csv_files/track.csv",header=True);

# Displays the content of the DataFrame to stdout
# invLineDF.show(10)
# trackDF.show(10)

# Register the DataFrame as a SQL temporary view
invLineDF.createOrReplaceTempView("InvoiceLine")
trackDF.createOrReplaceTempView("Track")

# query taken from Assignment 1 - Official Solution
# https://www3.elearning.rwth-aachen.de/ws18/18ws-186186/assessment/
# Lists/LA_SampleSolutions/A01/Exercise_1_withSolutions.pdf

queryText = \
	"SELECT t.Name, count(t.Name) as PurchaseCount \
	FROM Track t, InvoiceLine i \
	WHERE t.TrackId = i.TrackId \
	Group By t.Name \
	Order By PurchaseCount \
	Desc Limit 5"

sqlDF = spark.sql(queryText)
sqlDF.show()
sqlDF.explain(True)
