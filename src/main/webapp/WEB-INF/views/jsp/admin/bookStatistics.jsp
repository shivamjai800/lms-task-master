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

</head>
<body>
<div class="content-section new">
    <nav th:replace="admin/adminNav :: nav"></nav>
    <span >
        <table class="table table-responsive-sm table-hover" >
            <thead>
                <th>S.No</th>
                <th> BookId</th>
                <th> title</th>
                <th> author</th>
                <th>Quantity</th>
                <th>Requests (This month)</th>
            </thead>
            <tbody>
                <tr th:class="table-primary"
                    th:each="statistic , iStat: ${statistics}" th:with="book=${statistic.getKey()},c=${statistic.getValue()}">

                    <td th:text="${iStat.count}"/>
                    <td th:text="${book.id}"/>
                    <td th:text="${book.title}"/>
                    <td th:text="${book.author}"/>
                    <td th:text="${book.quantity}"/>
                    <td th:text="${c}"/>

                </tr>
            </tbody>
        </table>
    </span>
    <div id="chart_div"></div>
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
            console.log(e)
            real_data[e.key.title] = e.value
        })
        console.log(real_data)
    }
    test();

    $(document).ready(function() {
        google.charts.load('current', {
            packages : [ 'corechart', 'bar' ]
        });
        google.charts.setOnLoadCallback(drawColumnChart);
    });
    function drawColumnChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Title');
        data.addColumn('number', 'Requests');
        Object.keys(real_data).forEach(function(key) {
            data.addRow([ key, real_data[key] ]);
        });
        var options = {
            title : 'Top 10 books stats',
            hAxis : {
                title : 'Title oF book',
            },
            vAxis : {
                title : 'Total Request'
            }
        };
        var chart = new google.visualization.ColumnChart(document
            .getElementById('chart_div'));
        chart.draw(data, options);
    }

</script>
</body>
</html>
