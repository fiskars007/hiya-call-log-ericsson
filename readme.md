## What are the libraries or frameworks you have chosen? and why?

* Java 1.8 -- for familiarity, lambdas for clearer Activity code
* AndroidX targeting API 28 -- modern Android project setup and tooling support
* No external dependencies except RecyclerView -- limit complexity

## If you had more time, what would you have done better?

* Support auto-refresh by observing call database
* Better permissions checking & handling
* Layout polish & design improvements

## What are some of the key architecture considerations?

* Separation of ListAdapter code from main Activity code
* Use RecyclerView for built-in ViewHolder-style design & ease of setup
* Create mapping of call status to resource string when Context is available
