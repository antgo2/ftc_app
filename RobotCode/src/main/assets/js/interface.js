var origin = $("meta[name='origin']").attr("content");
var socket = new WebSocket("ws://" + origin);
socket.onmessage = function(message) {
  var part1 = message.data.split(";");
  var part2 = part1[1].split(",");
  var type = part1[0];
  var extension = part2[0];
  var data = part2[1];
  if(type == "text/config") {
    data.split("&").forEach(function(pair) {
        pair = pair.split("=");
        if (pair[0].indexOf(".") == -1) {
          pair[0] = "." + pair[0];
        }
        var parts = pair[0].split(".");
        var label = $("<label>").text(parts[parts.length - 1] + " ");
        var input = $("<input/>").attr({
          type: "text",
          value: pair[1],
          key: pair[0]
        });
        if (pair[1].indexOf(".") != -1) {
          input.attr({ step: "0.1" });
        }
        if ($.isNumeric(pair[1])) {
          input.attr({ type: "number" });
        }
        input.appendTo(label);
        label.append($("<br/>"));
        for (var i = 0; i < parts.length; i++) {
          if (i === parts.length - 1) continue;
          if (!$("#" + parts[i] + i).length) {
            var fieldset = $("<fieldset>").attr({ id: parts[i] + i });
            var arrow = $("<arrow>")
              .attr({ class: "down" })
              .click(function() {
                $(this).toggleClass("down up");
                $(this)
                  .parent()
                  .siblings()
                  .toggle();
              });
            var legend = $("<legend>")
              .text(parts[i])
              .prepend(arrow);
            fieldset.append(legend);
            $("body").append(fieldset);
          }
        }
        for (var i2 = parts.length - 2; i2 > 0; i2--) {
          $("#" + parts[i2] + i2).appendTo("#" + parts[i2 - 1] + (i2 - 1));
        }
        $("#" + parts[parts.length - 2] + (parts.length - 2)).append(label);
    });
  }
};
$("input").change(function() {
  socket.send("text/config;," + $(this).attr("key") + "=" + $(this).val());
});
$(window).unload(function() {
  socket.close();
});