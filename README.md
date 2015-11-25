# eos4j
Events oriented system for java

//Create edl System with edl parameters
 ExampleSystem exampleSystem = new ExampleSystem();
 
 //Create callback for any event
 EDLCallback callback = new EDLCallback() {
    @Override
    public List<String> getParametersNamesForDesiredValues() {
      return Arrays.asList("a.b.a");
    }

   @Override
    public void onEventHappened(Event event, Map<String, Object> desiredValues) {
      if (desiredValues.containsKey("a.b.a")) {
       System.out.println("Событие произошло в системе! + a.b.a: " + desiredValues.get("a.b.a"));
     }
    }
  };
//Add event to edl System
exampleSystem.getEventsHolder().addEvent(new Event(0, "test", 0, "a.a - b + a.b.a - c == -50"), callback);

...
//Set A parameter to 20 for test event
exampleSystem.getA().setA(20);
