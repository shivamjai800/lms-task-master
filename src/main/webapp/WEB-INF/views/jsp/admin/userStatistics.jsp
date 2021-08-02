<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta title="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">
    <script type="text/javascript"
    src="https://www.gstatic.com/charts/loader.js"></script>

    <title></title>
    <style>

        .table-image {td , th { vertical-align:middle;

                      }
        }

    </style>
    <script type="text/javascript"
    src="https://www.gstatic.com/charts/loader.js"></script>

</head>
<body>
<div class="content-section new">
    <nav th:replace="admin/adminNav :: nav"></nav>
    <span >
        <table class="table table-responsive-sm table-hover" >
            <thead>
                <th>S.No</th>
                <th> Username</th>
                <th> name</th>
                <th> role</th>
                <th>Requests (This month)</th>
            </thead>
            <tbody>
                <tr th:class="table-success"
                    th:each="statistic , iStat: ${statistics}" th:with="user=${statistic.getKey()},c=${statistic.getValue()}">

                    <td th:text="${iStat.count}"/>
                    <td th:text="${user.username}"/>
                    <td th:text="${user.name}"/>
                    <td th:text="${user.roles}"/>
                    <td th:text="${c}"/>

                </tr>
            </tbody>
        </table>
    </span>
    <div id="piechart" style="width: 900px; height: 500px;"></div>


</div>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
<script
        src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
<script th:inline="javascript">
    var real_data = {}
    function test()
    {
        let x = [[${statistics}]]
        x.forEach( (e) => {
            real_data[e.key.username] = e.value
        })
    }
    test();

    $(document).ready(function() {
        google.charts.load('current', {
            packages : [ 'corechart', 'bar' ]
        });
        // google.charts.setOnLoadCallback(drawColumnChart);
        google.charts.setOnLoadCallback(drawPieChart);
    });
    function drawPieChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'username');
        data.addColumn('number', 'number of request');
        Object.keys(real_data).forEach(function(key) {
            data.addRow([ key, real_data[key] ]);
        });
        var options = {
            title : 'Top User with their % contribution'
        };
        // console.log("data  = ",data)
        var chart = new google.visualization.PieChart(document
            .getElementById('piechart'));
        chart.draw(data, options);
    }
</script>
</body>
</html>
