# eos4j
Events oriented system for java

<br /> //Create edl System with edl parameters
<br /> ExampleSystem exampleSystem = new ExampleSystem();
<br /> //Create callback for any event
<br /> EDLCallback callback = new EDLCallback() {
<br />   @Override
<br />   public List<String> getParametersNamesForDesiredValues() {
<br />     return Arrays.asList("a.b.a");
<br />   }
<br />   @Override
<br />    public void onEventHappened(Event event, Map<String, Object> desiredValues) {
<br />     if (desiredValues.containsKey("a.b.a")) {
<br />       System.out.println("Событие произошло в системе! + a.b.a: " + desiredValues.get("a.b.a"));
<br />    }
<br />   }
<br /> };
<br /> //Add event to edl System
<br /> exampleSystem.getEventsHolder().addEvent(new Event(0, "test", 0, "a.a - b + a.b.a - c == -50"), callback);
<br />
<br /> ...
<br /> //Set A parameter to 20 for test event
<br /> exampleSystem.getA().setA(20);
