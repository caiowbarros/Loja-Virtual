21:06:50 PIT >> INFO : Verbose logging is disabled. If you encounter a problem, please enable it before reporting an issue.
21:06:51 PIT >> INFO : Incremental analysis reduced number of mutations by 0
21:06:51 PIT >> INFO : Created  8 mutation test units in pre scan
21:06:51 PIT >> INFO : Sending 54 test classes to minion
21:06:51 PIT >> INFO : Sent tests to minion
21:06:51 PIT >> INFO : MINION : 21:06:51 PIT >> INFO : Checking environment
21:06:52 PIT >> INFO : MINION : 21:06:52 PIT >> INFO : Found  33 tests
21:06:52 PIT >> INFO : MINION : 21:06:52 PIT >> INFO : Dependency analysis reduced number of potential tests by 0
21:06:52 PIT >> INFO : MINION : 21:06:52 PIT >> INFO : 33 tests received
21:06:53 PIT >> INFO : MINION : 21:06:53 PIT >> WARNING : More threads at end of test (6) TestaCompra(br.uff.loja.LojaApplicationTests) than start. (5)
/21:06:57 PIT >> INFO : Calculated coverage in 6 seconds.
21:06:57 PIT >> INFO : Incremental analysis reduced number of mutations by 0
21:06:57 PIT >> INFO : Created  8 mutation test units
/21:07:26 PIT >> WARNING : Minion exited abnormally due to TIMED_OUT
|21:07:27 PIT >> INFO : Completed in 37 seconds
================================================================================
- Mutators
================================================================================
> org.pitest.mutationtest.engine.gregor.mutators.BooleanTrueReturnValsMutator
>> Generated 4 Killed 3 (75%)
> KILLED 3 SURVIVED 1 TIMED_OUT 0 NON_VIABLE 0 
> MEMORY_ERROR 0 NOT_STARTED 0 STARTED 0 RUN_ERROR 0 
> NO_COVERAGE 0 
--------------------------------------------------------------------------------
> org.pitest.mutationtest.engine.gregor.mutators.EmptyObjectReturnValsMutator
>> Generated 11 Killed 11 (100%)
> KILLED 11 SURVIVED 0 TIMED_OUT 0 NON_VIABLE 0 
> MEMORY_ERROR 0 NOT_STARTED 0 STARTED 0 RUN_ERROR 0 
> NO_COVERAGE 0 
--------------------------------------------------------------------------------
> org.pitest.mutationtest.engine.gregor.mutators.ConditionalsBoundaryMutator
>> Generated 3 Killed 1 (33%)
> KILLED 1 SURVIVED 2 TIMED_OUT 0 NON_VIABLE 0 
> MEMORY_ERROR 0 NOT_STARTED 0 STARTED 0 RUN_ERROR 0 
> NO_COVERAGE 0 
--------------------------------------------------------------------------------
> org.pitest.mutationtest.engine.gregor.mutators.VoidMethodCallMutator
>> Generated 19 Killed 15 (79%)
> KILLED 14 SURVIVED 2 TIMED_OUT 1 NON_VIABLE 0 
> MEMORY_ERROR 0 NOT_STARTED 0 STARTED 0 RUN_ERROR 0 
> NO_COVERAGE 2 
--------------------------------------------------------------------------------
> org.pitest.mutationtest.engine.gregor.mutators.NullReturnValsMutator
>> Generated 15 Killed 13 (87%)
> KILLED 13 SURVIVED 2 TIMED_OUT 0 NON_VIABLE 0 
> MEMORY_ERROR 0 NOT_STARTED 0 STARTED 0 RUN_ERROR 0 
> NO_COVERAGE 0 
--------------------------------------------------------------------------------
> org.pitest.mutationtest.engine.gregor.mutators.MathMutator
>> Generated 1 Killed 1 (100%)
> KILLED 1 SURVIVED 0 TIMED_OUT 0 NON_VIABLE 0 
> MEMORY_ERROR 0 NOT_STARTED 0 STARTED 0 RUN_ERROR 0 
> NO_COVERAGE 0 
--------------------------------------------------------------------------------
> org.pitest.mutationtest.engine.gregor.mutators.BooleanFalseReturnValsMutator
>> Generated 4 Killed 3 (75%)
> KILLED 3 SURVIVED 1 TIMED_OUT 0 NON_VIABLE 0 
> MEMORY_ERROR 0 NOT_STARTED 0 STARTED 0 RUN_ERROR 0 
> NO_COVERAGE 0 
--------------------------------------------------------------------------------
> org.pitest.mutationtest.engine.gregor.mutators.NegateConditionalsMutator
>> Generated 30 Killed 25 (83%)
> KILLED 25 SURVIVED 5 TIMED_OUT 0 NON_VIABLE 0 
> MEMORY_ERROR 0 NOT_STARTED 0 STARTED 0 RUN_ERROR 0 
> NO_COVERAGE 0 
--------------------------------------------------------------------------------
================================================================================
- Timings
================================================================================
> pre-scan for mutations : < 1 second
> scan classpath : < 1 second
> coverage and dependency analysis : 6 seconds
> build mutation tests : < 1 second
> run mutation analysis : 30 seconds
--------------------------------------------------------------------------------
> Total  : 36 seconds
--------------------------------------------------------------------------------
================================================================================
- Statistics
================================================================================
>> Line Coverage: 122/130 (94%)
>> Generated 87 mutations Killed 72 (83%)
>> Mutations with no coverage 2. Test strength 85%
>> Ran 122 tests (1.4 tests per mutation)