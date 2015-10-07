$(function () {
			//Script for iCheck
			$('input').iCheck({
				checkboxClass: 'icheckbox_square-blue',
				radioClass: 'iradio_square-blue',
				increaseArea: '20%' // optional
			});

			//Script for bootstrap datepicker
			$('#doj').datepicker({
				format: 'mm/dd/yyyy',
				todayHighlight: true,
			});
			$('#assessmentFromDate').datepicker({
				format: 'dd/mm/yyyy',
				todayHighlight: true,
				autoclose: true
			});
			$('#assessmentToDate').datepicker({
				format: 'dd/mm/yyyy',
				todayHighlight: true,
				autoclose: true
			});
			$('#cyclestartdate').datepicker({
				format: 'mm/dd/yyyy',
				todayHighlight: true,
			});
			$('#cycleenddate').datepicker({
				format: 'mm/dd/yyyy',
				todayHighlight: true,
			});
			$('#selfr_completedon').datepicker({
				format: 'mm/dd/yyyy',
				todayHighlight: true,
			});
			$('#selfreporting_durationfrom1,#selfreporting_durationfrom2,#selfreporting_durationfrom3').datepicker({
				format: 'mm/dd/yyyy',
				todayHighlight: true,
			});
			$('#selfreporting_durationto1,#selfreporting_durationto2,#selfreporting_durationto3').datepicker({
				format: 'mm/dd/yyyy',
				todayHighlight: true,
			});
			$('#tkg_fromdate1, #tkg_fromdate2, #tkg_fromdate3').datepicker({
				format: 'mm/dd/yyyy',
				todayHighlight: true,
			});
			$('#tkg_todate1, #tkg_todate2, #tkg_todate3').datepicker({
				format: 'mm/dd/yyyy',
				todayHighlight: true,
			});

			//Script for datatable
			$("#emp_master_list").DataTable({
				"paging": true,
				"lengthChange": false,
				"searching": false,
				"ordering": true,
				"info": true,
				"autoWidth": false,
			});
			$("#viewedit_empselfreporting").DataTable({
				"paging": true,
				"lengthChange": false,
				"searching": false,
				"ordering": true,
				"info": true,
				"autoWidth": true,
			});
			$("#selfreporting_majorareaofwork").DataTable({
				"paging": false,
				"lengthChange": false,
				"searching": false,
				"ordering": false,
				"info": false,
				"autoWidth": false,
			});
			$("#selfreporting_bestperformroles").DataTable({
				"paging": false,
				"lengthChange": false,
				"searching": false,
				"ordering": false,
				"info": false,
				"autoWidth": false,
			});
			$("#selfreporting_facilitatinginhibiting").DataTable({
				"paging": false,
				"lengthChange": false,
				"searching": false,
				"ordering": false,
				"info": false,
				"autoWidth": false,
			});
			$("#selfreporting_additionalresponsibility").DataTable({
				"paging": false,
				"lengthChange": false,
				"searching": false,
				"ordering": false,
				"info": false,
				"autoWidth": false,
			});
			$("#selfreporting_trainingknowledge").DataTable({
				"paging": false,
				"lengthChange": false,
				"searching": false,
				"ordering": false,
				"info": false,
				"autoWidth": false,
			});
			$("#pmsstatusreport").DataTable({
				"paging": true,
				"lengthChange": false,
				"searching": false,
				"ordering": true,
				"info": true,
				"autoWidth": false,
			});
			$("#pmscompletionreport").DataTable({
				"paging": true,
				"lengthChange": false,
				"searching": false,
				"ordering": true,
				"info": true,
				"autoWidth": false,
			});


			//Script for adding classes on the parent div of grid table to apply responsiveness on grid on smaller devices
			$("#emp_master_list").parent().addClass("table-responsive custom-grid");
			$("#viewedit_empselfreporting").parent().addClass("table-responsive custom-grid");
			$("#selfreporting_majorareaofwork").parent().addClass("table-responsive custom-grid");
			$("#selfreporting_bestperformroles").parent().addClass("table-responsive custom-grid");
			$("#selfreporting_facilitatinginhibiting").parent().addClass("table-responsive custom-grid");
			$("#selfreporting_additionalresponsibility").parent().addClass("table-responsive custom-grid");
			$("#selfreporting_trainingknowledge").parent().addClass("table-responsive custom-grid");
			$("#pmsstatusreport").parent().addClass("table-responsive custom-grid");
			$("#pmscompletionreport").parent().addClass("table-responsive custom-grid");


			//Script for multiselect
			$('#multiselect').multiselect();

			//Script for inpromtu modal window
			$("#initiatereporting").click(function(e) {
					$.prompt("Are you sure you would like to initiate Self Reporting?", {
					buttons: { "Yes": true, "No": false }
				});
				 e.preventDefault();
			});

			$("#forgotuserid").click(function(e) {
					$.prompt("Please contact network admin for user id", {
					buttons: { "OK": true }
				});
				 e.preventDefault();
			});

			$("#forgotpassword").click(function(e) {
					$.prompt("Please contact network admin for password", {
					buttons: { "OK": true }
				});
				 e.preventDefault();
			});


			//Script for typeahead for input
			var substringMatcher = function(strs) {
				return function findMatches(q, cb) {
					var matches, substringRegex;

					// an array that will be populated with substring matches
					matches = [];

					// regex used to determine if a string contains the substring `q`
					substrRegex = new RegExp(q, 'i');

					// iterate through the pool of strings and for any string that
					// contains the substring `q`, add it to the `matches` array
					$.each(strs, function(i, str) {
						if (substrRegex.test(str)) {
							matches.push(str);
						}
					});
						cb(matches);
				};
			};
			var empnames = ['Aniruddha Joshi', 'Anup Patil', 'Archana Lal', 'Barnali Bhattacharya', 'Bhushan Kavthekar',
					  'Bhavesh Patil', 'Bharati Patil', 'Deepak Suri', 'Deepak Kadam', 'Fahad Inamdar', 'Ganesh Jadhav', 'Ganesh P Chellapan', 'Hariharan Iyer', 'Ipsita Chatterjee', 'Ian Southward', 'Jagdish Narayandasani', 'Kajal Uklekar', 'Kamal Pandit', 'Shailaja Patole', 'Sandeep Mane', 'Nandani Bhatnagar', 'Paresh Lagdhir', 'Reshma Sachdev', 'Tushar Chitnis', 'Raphael Fernandes', 'Makrand Zalkikar', 'Chandrashekhar Sonde', 'Nirav Upadhyay', 'Vishakha Sawant', 'Vedprakash Singh', 'Rahul Bansal', 'Chetan Naik', 'Ravi Kumar JVK', 'Rakesh Agarwal', 'Shilpa Nevase', 'Praveen Mooli', 'Sudhir Kancherla', 'Rahul Pathak', 'Asmita Panchagnula', 'Shailendra Matkar', 'Sonal Verma', 'Sonal Nagda', 'Tanya Ganguli', 'Ritesh Sharma', 'Ravindra Pawar', 'Sanjay Saxena', 'Satyavati Mishra', 'Manoranjan Kumar', 'Nikhila Jain', 'Vishal Mehta', 'Sandeep Maher', 'Ranjeet Adkar', 'Swati Zambre', 'Juni Stephen'
					];


				$('#vieweditselfreportempname_container .typeahead, #normalisation_searchcontainer .typeahead').typeahead({
					hint: true,
					highlight: true,
					minLength: 1
				},
				{
					name: 'empnames',
					source: substringMatcher(empnames)
				});

				//Sesssion Timeout Management
				$.sessionTimeout({
					  warnAfter: 1620000,
					  redirAfter: 1800000
					});



				//Script for popover of copy to other employee

				//$("[data-toggle='popover']").popover();

				$('[data-toggle=popover]').popover({
					placement: 'bottom',
					//container: 'body',
					content: function () {
						return $(this.dataset.pop).html();
					},
					html: true,
				});


				//Script for closing the popover window
				$('[data-toggle="popover"]').each(function () {
					var button = $(this);
					button.popover().on('shown.bs.popover', function() {
						button.data('bs.popover').tip().find('[data-dismiss="popover"]').on('click', function () {
							button.popover('toggle');
						});
					});
				});

				$('body').on('click', function (e) {
					$('[data-toggle="popover"]').each(function () {
						//the 'is' for buttons that trigger popups
						//the 'has' for icons within a button that triggers a popup
						if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
							$(this).popover('hide');
						}
					});
				});


				//Script for toggling the arrow icons
				function toggleChevron(e) {
					$(e.target).prev('.panel-heading').find("i.indicator").toggleClass('glyphicon-plus glyphicon-minus');
				}
					$('#main-accordion').on('hidden.bs.collapse', toggleChevron);
					$('#main-accordion').on('shown.bs.collapse', toggleChevron);





});